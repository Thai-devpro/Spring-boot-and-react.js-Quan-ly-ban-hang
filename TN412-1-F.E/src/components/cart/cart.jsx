import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Button, Container, Table } from 'reactstrap';
import { formatNumber } from '../../helpers/utils';
import CartService from '../../services/cart.service';
import './cart.css';
const Cart = () => {
	const [carts, setCarts] = useState([]);
	useEffect(() => {
		CartService.getAllCart().then((res) => {
			if (res.data) {
				setCarts(res.data);
			}
		});
	}, []);
	const deleteCart = (id) => {
		CartService.deleteCart(id).then((res) => {
			if (res.data) {
				let updatedCarts = [...carts].filter((i) => i.id !== id);
				setCarts(updatedCarts);
			}
		});
	};
	const cartList = carts.map((cart, index) => {
		return (
			<tr key={cart.id}>
				<td>
					<img
						className="image"
						src={'http://localhost:8089/api/product/image/' + cart.product.photo}
						alt="" />
				</td>
				<td>{cart.product.name}</td>
				<td>{formatNumber(cart.product.price)}</td>

				<td>
					
					{ cart.quantity }
					
				</td>


				<td>{formatNumber(cart.product.price * cart.quantity)}</td>
				<td>
					<Button size="sm" color="danger" onClick={() => deleteCart(cart.id)}>
						Xóa
					</Button>
				</td>

			</tr>

		);
	});

	const handleTotalPrice = () => {
		var totalPrice = 0;
		if (carts.length > 0) {
			carts.forEach((item) => {
				const price = item.product.price * item.quantity;
				totalPrice += price;
			});
		}
		return totalPrice;
	};
	return (
		<div className="wrapper">
			<Container fluid>
				<Table className="mt-4">
					<thead>
						<tr>
							<th width="25%">Hình ảnh</th>
							<th width="25%">Tên</th>
							<th width="20%">Giá</th>
							<th width="10%">Số lượng</th>
							<th width="20%">Thành tiền</th>
						</tr>
					</thead>
					<tbody>{cartList}</tbody>


				</Table>
				{handleTotalPrice() > 0 && <p>Tổng tiền : {formatNumber(handleTotalPrice())}</p>}
				
				{carts.length === 0 && (
					<div className="no-cart">
						<p>Giỏ hàng của bạn hiện đang trống!</p>
						<Link to={'/products'} className="navbar-brand">
							<button type="button" className="btn btn-primary">
								Tiếp tục mua sắm
							</button>
						</Link>
					</div>
				)}
			</Container>
		</div>
	);
};

export default Cart;