"use client"

// 제품 데이터 타입 정의
import {useState} from "react";

interface Product {
    imageSrc: string;
    name: string;
    status: 'active' | 'pending' | 'approved' | 'isDeleted';
    price: number;
    totalSales: number;
    createdAt: string;
}

// 초기 제품 목록 (예시 데이터)
const products: Product[] = [
    {
        imageSrc: 'path/to/image1.jpg',
        name: 'Smartphone X Pro',
        status: 'active',
        price: 999.0,
        totalSales: 150,
        createdAt: '6/23/2024',
    },
    {
        imageSrc: 'path/to/image2.jpg',
        name: 'Wireless Earbuds Ultra',
        status: 'active',
        price: 199.0,
        totalSales: 300,
        createdAt: '6/23/2024',
    },
    {
        imageSrc: 'path/to/image3.jpg',
        name: 'Smart Home Hub',
        status: 'pending',
        price: 149.0,
        totalSales: 200,
        createdAt: '6/23/2024',
    },
    {
        imageSrc: 'path/to/image4.jpg',
        name: '4K Ultra HD Smart TV',
        status: 'approved',
        price: 799.0,
        totalSales: 50,
        createdAt: '6/23/2024',
    },
    {
        imageSrc: 'path/to/image5.jpg',
        name: 'Gaming Laptop Pro',
        status: 'isDeleted',
        price: 1299.0,
        totalSales: 75,
        createdAt: '6/23/2024',
    },
];

const ProductTable: React.FC = () => {
    const [filter, setFilter] = useState<'All' | 'PENDING' | 'APPROVED' | 'ISDELETED'>('All');

    // 필터링된 데이터
    const filteredProducts =
        filter === 'All'
            ? products
            : products.filter((product) => product.status.toUpperCase() === filter);

    return (
        <div className="p-6">
            {/* 필터 메뉴 */}
            <div className="mb-4">
                <button
                    onClick={() => setFilter('All')}
                    className={`px-4 py-2 mr-2 ${filter === 'All' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
                >
                    All
                </button>
                <button
                    onClick={() => setFilter('PENDING')}
                    className={`px-4 py-2 mr-2 ${filter === 'PENDING' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
                >
                    PENDING
                </button>
                <button
                    onClick={() => setFilter('APPROVED')}
                    className={`px-4 py-2 mr-2 ${filter === 'APPROVED' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
                >
                    APPROVED
                </button>
                <button
                    onClick={() => setFilter('ISDELETED')}
                    className={`px-4 py-2 ${filter === 'ISDELETED' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
                >
                    ISDELETED
                </button>
            </div>

            {/* 테이블 */}
            <table className="w-full table-auto border-separate border-spacing-0">
                <thead>
                <tr className="text-left">
                    <th className="p-3">Image</th>
                    <th className="p-3">Name</th>
                    <th className="p-3">Status</th>
                    <th className="p-3">Price</th>
                    <th className="p-3">Total Sales</th>
                    <th className="p-3">Created At</th>
                    <th className="p-3">Actions</th>
                </tr>
                </thead>
                <tbody>
                {filteredProducts.map((product) => (
                    <tr key={product.name} className="border-b">
                        <td className="p-3">
                            <img src={product.imageSrc} alt={product.name} className="w-16 h-16 object-cover" />
                        </td>
                        <td className="p-3">{product.name}</td>
                        <td className="p-3">{product.status}</td>
                        <td className="p-3">${product.price.toFixed(2)}</td>
                        <td className="p-3">{product.totalSales}</td>
                        <td className="p-3">{product.createdAt}</td>
                        <td className="p-3">
                            <button className="text-blue-500 hover:text-blue-700">Toggle menu</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ProductTable;