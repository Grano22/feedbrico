import {Button, Card, Input, message as antdMessage} from 'antd';
import {useForm} from 'react-hook-form';
import {zodResolver} from '@hookform/resolvers/zod';
import {type Feedback, feedbackSchema} from "../feedback.ts";

function SendFeedbackPage() {
    const { register, handleSubmit, formState: { errors }, reset } = useForm<Feedback>({
        resolver: zodResolver(feedbackSchema),
    });

    const onSubmit = (data: Feedback) => {
        void antdMessage.success('Feedback sent! (demo)');
        console.log(data);
        reset();
    };

    return (
        <div className="flex items-center justify-center h-full">
            <Card className="max-w-xl w-full p-8 bg-white dark:bg-gray-900 shadow-lg">
                <h2 className="mb-6 text-2xl font-bold text-primary">Send Feedback</h2>
                <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
                    <div>
                        <label className="block mb-1 font-semibold">Name</label>
                        <Input {...register('name')} maxLength={36} />
                        {errors.name && <span className="text-red-500">{errors.name.message}</span>}
                    </div>
                    <div>
                        <label className="block mb-1 font-semibold">Email</label>
                        <Input {...register('email')} maxLength={36} />
                        {errors.email && <span className="text-red-500">{errors.email.message}</span>}
                    </div>
                    <div>
                        <label className="block mb-1 font-semibold">Message</label>
                        <Input.TextArea {...register('message')} rows={6} maxLength={65535} />
                        {errors.message && <span className="text-red-500">{errors.message.message}</span>}
                    </div>
                    <Button type="primary" htmlType="submit" block>
                        Submit
                    </Button>
                </form>
            </Card>
        </div>
    );
}

export default SendFeedbackPage;