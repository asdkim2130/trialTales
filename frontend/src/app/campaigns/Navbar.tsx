"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { getUserSession } from "./getUserSession";
import styles from "./navbar.module.css";

export default function Navbar() {
    const router = useRouter();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        // ✅ 서버에서 로그인 상태 가져오기
        getUserSession().then((session) => {
            setIsLoggedIn(session.isLoggedIn);
        });
    }, []);

    const handleLogout = () => {
        document.cookie = "token=; path=/; max-age=0"; // ✅ 토큰 삭제 (쿠키 만료)
        setIsLoggedIn(false);
        router.push("/campaigns"); // ✅ 로그아웃 후 campaigns로 이동
    };

    return (
        <nav className={styles.navbar}>
            <div className={styles.logo} onClick={() => router.push("/campaigns")}>
                🚀 Trial Tales
            </div>
            <ul className={styles.menu}>
                {!isLoggedIn ? (
                    <li>
                        <button className={styles.navButton} onClick={() => router.push("/members/login")}>
                            로그인
                        </button>
                    </li>
                ) : (
                    <>
                        <li>
                            <button className={styles.navButton} onClick={() => router.push("/members/profile")}>
                                프로필
                            </button>
                        </li>
                        <li>
                            <button className={styles.navButton} onClick={handleLogout}>
                                로그아웃
                            </button>
                        </li>
                    </>
                )}
            </ul>
        </nav>
    );
}
