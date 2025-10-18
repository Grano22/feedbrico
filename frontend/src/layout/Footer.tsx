const Footer = () => {
    return (
        <footer className="w-full fixed bottom-0 left-0 bg-gray-100 dark:bg-gray-800 p-4 text-center text-sm text-gray-700 dark:text-gray-300">
            &copy; {new Date().getFullYear()} Feedbrico, Adrian Błasiak. All rights reserved.
        </footer>
    );
}

export default Footer;