import {Link} from "react-router";

function Sidebar() {
    return (
        <aside className="w-60 bg-white dark:bg-gray-950 shadow flex flex-col min-h-full">
            <nav className="flex flex-col p-4 gap-2">
                <Link className="text-lg text-primary" to="/">🏠 Home</Link>
                <Link className="text-lg text-primary" to="/send-feedback">✉️ Feedback</Link>
            </nav>
        </aside>
    );
}

export default Sidebar;