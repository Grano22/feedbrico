import {z} from "zod";

export const feedbackSchema = z.object({
    name: z.string({ error: 'Name is required' }).min(1, 'Name is required').max(64, 'Max 64 characters'),
    email: z.email({ error: 'Invalid email' }).max(64, 'Max 64 characters'),
    message: z.string({ error: 'Message is required' }).min(1, 'Message is required').max(65535, 'Max 64K'),
});

export type Feedback = z.infer<typeof feedbackSchema>;