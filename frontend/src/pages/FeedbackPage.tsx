import {Alert, Button, Card, Input, message as antdMessage, Modal} from 'antd';
import {Controller, useForm} from 'react-hook-form';
import {zodResolver} from '@hookform/resolvers/zod';
import {type Feedback, feedbackSchema} from "../feedback.ts";
import {type FC, useCallback, useEffect, useState} from "react";
import {debounce} from "../util/debounce.ts";
import {useNavigate} from "react-router";
import {feedbackSubmissionErrorSchema, feedbackSubmissionSuccessSchema} from "../feedbackSubmission.ts";

const SendFeedbackPage: FC = () => {
    const {
        handleSubmit,
        control,
        formState: { errors, dirtyFields },
        reset,
        watch,
        trigger
    } = useForm<Feedback>({
        resolver: zodResolver(feedbackSchema),
        mode: 'onChange',
        defaultValues: {
            name: '',
            email: '',
            message: ''
        }
    });
    const navigate = useNavigate();

    const [mainError, setMainError] = useState<string | null>(null);
    const [modalOpen, setModalOpen] = useState(false);
    const handleModalClose = () => {
        setModalOpen(false);
        navigate("/");
    };

    const debouncedTrigger = useCallback(debounce((field: keyof Feedback) => {
        void trigger(field);
    }, 250), [ trigger ]);

    const watchedFields = watch();
    useEffect(() => {
        if (dirtyFields.name !== undefined) {
            debouncedTrigger('name');
        }
    }, [watchedFields.name, dirtyFields.name, debouncedTrigger]);
    useEffect(() => {
        if (dirtyFields.email !== undefined) {
            debouncedTrigger('email');
        }
    }, [watchedFields.email, dirtyFields.email, debouncedTrigger]);
    useEffect(() => {
        if (dirtyFields.message !== undefined) {
            debouncedTrigger('message');
        }
    }, [watchedFields.message, dirtyFields.message, debouncedTrigger]);


    const onSubmit = async (data: Feedback) => {
        try {
            const response = await fetch(`/api/feedback/v1/`, {
                method: 'POST',
                body: JSON.stringify(data),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                const errorResponse = feedbackSubmissionErrorSchema.parse(await response.json());

                setMainError(errorResponse.message || 'Network error or server is inaccessible.');

                return;
            }

            const successResponse = feedbackSubmissionSuccessSchema.parse(await response.json());

            console.debug(`Received feedback details`, successResponse);

            setModalOpen(true);
            reset();
        } catch (err) {
            setMainError('Problem occurred from the server side. Please, try again later or contact with our support directly');
            console.debug(err);
        }
    };

    return (
        <section className="flex items-center justify-center w-full min-h-[calc(100vh-64px-56px)] pt-4 pb-8">
            <Card
                className="w-full max-w-2xl mx-auto bg-white dark:bg-gray-900 rounded-xl shadow-2xl border border-gray-200 dark:border-gray-800"
                styles={{ body: { padding: '2.5rem 2rem' } }}
            >
                <h2 className="text-2xl md:text-3xl font-bold text-center text-primary mb-10">Send Feedback</h2>
                <form onSubmit={handleSubmit(onSubmit)} className="grid gap-7">
                    <div>
                        <label className="block mb-2 font-semibold text-slate-900 dark:text-gray-300">Name</label>
                        <Controller
                            name="name"
                            control={control}
                            render={({ field }) => (
                                <Input
                                    {...field}
                                    maxLength={36}
                                    size="large"
                                    className={`rounded focus:ring-primary focus:border-primary ${errors.name ? 'border-red-400' : ''}`}
                                    placeholder="Your name"
                                />
                            )}
                        />
                        {errors.name && (
                            <div className="mt-1 text-red-500 text-sm font-medium">{errors.name.message}</div>
                        )}
                    </div>
                    <div>
                        <label className="block mb-2 font-semibold text-slate-900 dark:text-gray-300">Email</label>
                        <Controller
                            name="email"
                            control={control}
                            render={({ field }) => (
                                <Input
                                    {...field}
                                    maxLength={36}
                                    size="large"
                                    className={`rounded focus:ring-primary focus:border-primary ${errors.email ? 'border-red-400' : ''}`}
                                    placeholder="Your email"
                                />
                            )}
                        />
                        {errors.email && (
                            <div className="mt-1 text-red-500 text-sm font-medium">{errors.email.message}</div>
                        )}
                    </div>
                    <div>
                        <label className="block mb-2 font-semibold text-slate-900 dark:text-gray-300">Message</label>
                        <Controller
                            name="message"
                            control={control}
                            render={({ field }) => (
                                <Input.TextArea
                                    {...field}
                                    rows={8}
                                    maxLength={65535}
                                    size="large"
                                    className={`rounded focus:ring-primary focus:border-primary ${errors.message ? 'border-red-400' : ''}`}
                                    placeholder="Type your feedback..."
                                />
                            )}
                        />

                        {errors.message && (
                            <div className="mt-1 text-red-500 text-sm font-medium">{errors.message.message}</div>
                        )}
                    </div>
                    {mainError && (
                        <Alert
                            className="mb-4"
                            message="Submission failed"
                            description={mainError}
                            type="error"
                            showIcon
                            closable
                            onClose={() => setMainError(null)}
                        />
                    )}
                    <Button type="primary" htmlType="submit" size="large" className="w-full mt-2 rounded">
                        Submit
                    </Button>
                </form>
            </Card>

            <Modal
                open={modalOpen}
                centered
                footer={null}
                onCancel={handleModalClose}
                afterClose={handleModalClose}
            >
                <div className="flex flex-col items-center justify-center py-8">
                    <div className="text-green-500 text-4xl mb-4">
                        <i className="ri-checkbox-circle-fill" /> {/* Use any icon lib or Antd icon */}
                    </div>
                    <h3 className="text-xl font-bold mb-2">
                        Thank you for your feedback!
                    </h3>
                    <div className="text-gray-500 mb-6">
                        Your message was sent successfully.
                    </div>
                    <Button type="primary" onClick={handleModalClose}>
                        Back to Home
                    </Button>
                </div>
            </Modal>
        </section>
    );
}

export default SendFeedbackPage;