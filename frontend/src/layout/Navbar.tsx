import type {FC} from "react";
import {Link} from "react-router";
import {Button} from "antd";
import { BulbOutlined, BulbFilled } from '@ant-design/icons';
import {useTheme} from "../hooks/ussTheme.tsx";

const Navbar: FC = () => {
    const { isDark, toggleTheme } = useTheme();

    return (
        <header className="flex items-center justify-between px-6 py-3 bg-white dark:bg-gray-950 shadow">
            <span className="text-xl font-bold text-primary dark:text-primary-200">Feedbrico</span>
            <div className="flex items-center">
                <Link className="mr-6 text-primary" to="/">Home</Link>
                <Link className="mr-6 text-primary" to="/send-feedback">Send Feedback</Link>
                <Button
                    icon={isDark ? <BulbFilled /> : <BulbOutlined />}
                    onClick={toggleTheme}
                >
                    {isDark ? 'Dark' : 'Light'}
                </Button>
            </div>
        </header>
    )
}

export default Navbar;