"use client"
import {FormEvent, useState} from "react";
import { RegisterRequest } from "@/app/types/register";
import {register} from "@/app/services/auth-services";
import styles from "@/app/login/login.module.css";
import axios from "axios";
import { useRouter } from "next/navigation";

export default function RegisterPage() {
    const router = useRouter();

    const [registerRequest, setRegisterRequest] = useState<RegisterRequest>(
        {
            firstName:"",
            lastName:"",
            email:"",
            password:""
        }
    )
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);


    async function handleRegister(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setError("");
        setLoading(true);
        try {
            await register(registerRequest);
            router.push("/login");
        }
        catch (err) {
            if (axios.isAxiosError(err)) {
                console.log(err.response?.data);
                setError(err.response?.data.message );
            }

            else {
                setError("Something went wrong")
            }
        }
        finally {
            setLoading(false);
        }

    }

    return (
        <main className={styles.container}>
            <div className={styles.card}>

                <h1 className={styles.title}>
                    Register
                </h1>

                <p className={styles.subtitle}>
                    Book Social Network
                </p>

                {error && (
                    <p className={styles.error}>
                        {error}
                    </p>
                )}

                <form
                    className={styles.form}
                    onSubmit={handleRegister}
                >

                    <div className={styles.formGroup}>
                        <label>First Name</label>

                        <input
                            className={styles.input}
                            type="text"
                            value={registerRequest.firstName}
                            onChange={(e) =>
                                setRegisterRequest({
                                    ...registerRequest,
                                    firstName: e.target.value
                                })
                            }
                            required
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Last Name</label>

                        <input
                            className={styles.input}
                            type="text"
                            value={registerRequest.lastName}
                            onChange={(e) =>
                                setRegisterRequest({
                                    ...registerRequest,
                                    lastName: e.target.value
                                })
                            }
                            required
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Email</label>

                        <input
                            className={styles.input}
                            type="email"
                            value={registerRequest.email}
                            onChange={(e) =>
                                setRegisterRequest({
                                    ...registerRequest,
                                    email: e.target.value
                                })
                            }
                            required
                        />
                    </div>

                    <div className={styles.formGroup}>
                        <label>Password</label>

                        <input
                            className={styles.input}
                            type="password"
                            value={registerRequest.password}
                            onChange={(e) =>
                                setRegisterRequest({
                                    ...registerRequest,
                                    password: e.target.value
                                })
                            }
                            required
                        />
                    </div>

                    <button
                        className={styles.loginButton}
                        type="submit"
                        disabled={loading}
                    >
                        {loading ? "Registering..." : "Register"}
                    </button>

                </form>

                <div className={styles.registerSection}>
                    Already have an account?{" "}

                    <button
                        type="button"
                        className={styles.registerButton}
                        onClick={() => router.push("/login")}
                    >
                        Login
                    </button>
                </div>

            </div>
        </main>
    );
}