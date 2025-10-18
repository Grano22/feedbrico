import {type FC, useEffect} from 'react'
import './App.css'
import {ConfigProvider, theme} from "antd";
import {BrowserRouter, Route, Routes} from "react-router";
import Footer from "./layout/Footer.tsx";
import Sidebar from "./layout/Sidebar.tsx";
import {useTheme} from "./hooks/ussTheme.tsx";
import Navbar from "./layout/Navbar.tsx";
import HomePage from "./pages/HomePage.tsx";
import SendFeedbackPage from "./pages/FeedbackPage.tsx";

const App: FC = () => {
    const { isDark } = useTheme();

    useEffect(() => {
        document.documentElement.classList.toggle('dark', isDark);
        localStorage.setItem('theme', isDark ? 'dark' : 'light');
    }, [isDark]);

    return (
        <ConfigProvider
            theme={{
                algorithm: isDark ? theme.darkAlgorithm : theme.defaultAlgorithm,
                token: {
                    colorPrimary: '#635dff',
                    colorBgBase: isDark ? '#171923' : '#fff',
                },
            }}
        >
            <BrowserRouter>
                <div className="min-h-screen flex flex-col bg-gray-100 dark:bg-gray-900">
                    <Navbar/>
                    <div className="flex flex-1">
                        <Sidebar />
                        <main className="flex-1 p-6">
                            <Routes>
                                <Route path="/" element={<HomePage />} />
                                <Route path="/send-feedback" element={<SendFeedbackPage />} />
                            </Routes>
                        </main>
                    </div>
                    <Footer />
                </div>
            </BrowserRouter>
        </ConfigProvider>
    );
}

export default App
