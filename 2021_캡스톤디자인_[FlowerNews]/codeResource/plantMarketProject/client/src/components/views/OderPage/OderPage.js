import React, { useEffect, useState } from "react";
import "../OderPage/OderPage.css";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
} from "@material-ui/core";
import { USER_SERVER } from "../../Config.js";
import axios from "axios";

function createBuyerData(key, value) {
  return { key, value };
}
const rowsBuyer = [
  createBuyerData("이름", "홍길동"),
  createBuyerData("이메일", "hong@naver.com"),
  createBuyerData("휴대폰번호", "010-1010-2020"),
];

function createSellerData(key, value) {
  return { key, value };
}
const rowsSeller = [
  createSellerData("이름", "홍길동"),
  createSellerData("배송주소", "서울특별시 용산구 이촌동 436"),
  createSellerData("연락처", "010-1010-2020"),
  createSellerData("배송시 요청사항", "문 앞"),
];

function createPaymentrData(key, value) {
  return { key, value };
}
const rowsPayment = [
  createPaymentrData("총 상품가격", "97,630원"),
  createPaymentrData("할인쿠폰", "0원"),
  createPaymentrData("배송비", "0원"),
  createPaymentrData("총 결제 금액", "97,630원"),
  createPaymentrData("결제 방법", ""),
];

function OderPage() {
  return (
    <div id="entire-OderPage">
      <div className="main-grid">
        <div className="intro">
          <div className="title">주문/결제</div>
          <div className="category">장바구니&gt;주문결제&gt;주문완료</div>
        </div>
        <div className="contents">
          <div className="infoBox">
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                  <TableRow className="tableLow">
                    <TableCell style={{ width: "30%" }}>구매자정보</TableCell>
                    <TableCell></TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rowsBuyer.map((rowsBuyer) => (
                    <TableRow
                      key={rowsBuyer.key}
                      // sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell align="left">{rowsBuyer.key}</TableCell>
                      <TableCell>{rowsBuyer.value}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </div>

          <div className="infoBox">
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                  <TableRow className="tableLow">
                    <TableCell style={{ width: "30%" }}>받는사람정보</TableCell>
                    <TableCell></TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rowsSeller.map((rowsSeller) => (
                    <TableRow
                      key={rowsSeller.key}
                      // sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell align="left">{rowsSeller.key}</TableCell>
                      <TableCell>{rowsSeller.value}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </div>

          <div className="infoBox">
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                  <TableRow className="tableLow">
                    <TableCell style={{ width: "30%" }}>결제정보</TableCell>
                    <TableCell></TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rowsPayment.map((rowsPayment) => (
                    <TableRow
                      key={rowsPayment.key}
                      // sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell align="left">{rowsPayment.key}</TableCell>
                      <TableCell>{rowsPayment.value}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>

            <div className="buttonLayout">
              <Button variant="contained" color="secondary" className="btn">
                <div className="btn-font">결제하기</div>
              </Button>
              <Button variant="outlined" color="primary" className="btn">
                <div className="btn-font">결제취소</div>
              </Button>
            </div>
          </div>
        </div>
        <div className="footer">
          상호명 및 호스팅 서비스 제공 : Flower News
          <br />
          대표이사 / 사업자등록번호 / 통신판매업신고 / 고객센터
        </div>
      </div>
    </div>
  );
}

export default OderPage;
