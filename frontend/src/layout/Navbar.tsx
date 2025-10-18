import type {FC} from "react";
import {Link} from "react-router";
import {Button} from "antd";
import { BulbOutlined, BulbFilled } from '@ant-design/icons';
import {useTheme} from "../hooks/ussTheme.tsx";

const Navbar: FC = () => {
    const { isDark, toggleTheme } = useTheme();

    return (
        <nav className="w-full z-30 bg-white dark:bg-gray-950 border-b border-primary shadow-sm transition">
            <div className="max-w-7xl mx-auto flex items-center justify-between px-4 lg:px-8 py-3">
                <Link to="/" className="text-xl font-extrabold tracking-tight text-primary">
                    Feedback Portal
                </Link>
                <div className="flex items-center gap-8">
                    <Link
                        to="/"
                        className={`inline-block font-medium ${location.pathname === '/' ? 'text-blue-600 dark:text-blue-400' : 'text-gray-800 dark:text-gray-300'} hover:text-primary`}
                    >
                        Home
                    </Link>
                    <Link
                        to="/send-feedback"
                        className={`inline-block font-medium ${location.pathname === '/send-feedback' ? 'text-blue-600 dark:text-blue-400' : 'text-gray-800 dark:text-gray-300'} hover:text-primary`}
                    >
                        Send Feedback
                    </Link>
                    <Button
                        icon={isDark ? <BulbFilled /> : <BulbOutlined />}
                        onClick={toggleTheme}
                        style={{
                            background: isDark ? '#222' : '#f3f4f6',
                            color: isDark ? '#fff' : '#222',
                            border: 'none',
                            boxShadow: '0 1px 8px #0002',
                        }}
                    >
                        {isDark ? "Dark" : "Light"}
                    </Button>
                </div>
            </div>
        </nav>
    )
}

export default Navbar;