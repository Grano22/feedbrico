import {createContext, type FC, type PropsWithChildren, use, useEffect, useState} from "react";

type Theme = "light" | "dark";
type ThemeContextType = {
    theme: Theme,
    setTheme: (t: Theme) => void,
    toggleTheme: () => void,
};

const ThemeContext = createContext<ThemeContextType | undefined>(undefined);

export const ThemeProvider: FC<PropsWithChildren> = ({ children }) => {
    const [theme, updateTheme] = useState<Theme>(
        () => (localStorage.getItem("theme") as Theme) || "light"
    );

    useEffect(() => {
        document.documentElement.classList.toggle("dark", theme === "dark");
        localStorage.setItem("theme", theme);
    }, [theme]);

    const setTheme = (t: Theme) => { updateTheme(t); };
    const toggleTheme = () => updateTheme(t => (t === "dark" ? "light" : "dark"));

    return (
        <ThemeContext.Provider value={{ theme, setTheme, toggleTheme }}>
            {children}
        </ThemeContext.Provider>
    )
};

export const useTheme = () => {
    const ctx = use(ThemeContext);

    if (!ctx) throw new Error("useTheme must be used within ThemeProvider");
    const { theme, toggleTheme } = ctx;

    return { theme, toggleTheme, isDark: theme === "dark" };
};