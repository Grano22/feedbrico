import {z} from "zod";

export const feedbackSubmissionSuccessSchema = z.object({
    id: z.string(),
    name: z.string(),
    message: z.string()
});

export const feedbackSubmissionErrorSchema = z.object({ message: z.string() });