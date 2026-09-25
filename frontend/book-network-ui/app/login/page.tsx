"use client";

import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";
import { authenticate } from "@/app/services/auth-services";
import styles from "./login.module.css";
import {tokenService} from "@/app/services/token-service";

export default function LoginPage() {
    const router = useRouter();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        setError("");
        setLoading(true);

        try {
            const response = await authenticate({
                email,
                password,
            });

            tokenService.setToken(response.token);

            router.push("/books");
        } catch {
            setError("Invalid email or password");
        } finally {
            setLoading(false);
        }
    }

    return (
        <main className={styles.container}>
            <div className={styles.card}>

                <h1 className={styles.title}>Login</h1>

                <p className={styles.subtitle}>
                    Book Social Network
                </p>

                {error && (
                    <p className={styles.error}>{error}</p>
                )}

                <form className={styles.form} onSubmit={handleSubmit}>

                    <div className={styles.formGroup}>
                        <label>Email</label>

                        <input
                            className={styles.input}
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Password</label>

                        <input
                            className={styles.input}
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button
                        className={styles.loginButton}
                        type="submit"
                        disabled={loading}
                    >
                        {loading ? "Logging in..." : "Login"}
                    </button>

                </form>

                <div className={styles.registerSection}>
                    Don't have an account?{" "}

                    <button
                        className={styles.registerButton}
                        onClick={() => router.push("/register")}
                    >
                        Register
                    </button>
                </div>

            </div>
        </main>
    );
}