import {z} from "zod";

export const feedbackSchema = z.object({
    name: z.string().min(1, 'Name is required').max(36, 'Max 36 characters'),
    email: z.email('Invalid email').max(36),
    message: z.string().min(1, 'Message is required').max(65535, 'Max 64K'),
});

export type Feedback = z.infer<typeof feedbackSchema>;