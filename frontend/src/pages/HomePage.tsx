import { Button, Card } from 'antd';
import { useNavigate } from 'react-router';

function HomePage() {
    const nav = useNavigate();
    return (
        <div className="flex items-center justify-center h-full">
            <Card className="max-w-sm mx-auto shadow-lg bg-white dark:bg-gray-900">
                <h1 className="mb-6 text-2xl font-bold text-primary">Welcome!</h1>
                <p className="mb-6 text-slate-900 dark:text-gray-300">We value your feedback. Help us improve.</p>
                <Button type="primary" block onClick={() => nav('/send-feedback')}>
                    Give feedback
                </Button>
            </Card>
        </div>
    );
}

export default HomePage;