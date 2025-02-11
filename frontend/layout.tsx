"use client";

import { ReactNode } from "react";
import Navbar from "./src/app/campaigns/Navbar"; // ✅ 경로 수정
import styles from "./src/app/campaigns/layout.module.css"; // ✅ 레이아웃 스타일 경로 수정

interface LayoutProps {
    children: ReactNode;
}

export default function Layout({ children }: LayoutProps) {
    return (
        <div className={styles.layoutContainer}>
            {/* ✅ 네비게이션 바 */}
            <Navbar />

            {/* ✅ 메인 컨텐츠 */}
            <div className={styles.content}>
                {children}
            </div>
        </div>
    );
}
