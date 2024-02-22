import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import {
  Table,
  CheckBox,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Checkbox,
} from "@material-ui/core";

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

function createData(name, price, quantity, delivePrice, check) {
  return { name, price, quantity, delivePrice, check };
}

const rows = [
  createData("장미 꽃다발", 34000, 1, "무료", <Checkbox checked="true" />),
  createData("프리저드 플라워", 23000, 1, 2500, <Checkbox checked="true" />),
  createData("공기정화 식물", 167000, 1, "무료", <Checkbox checked="true" />),
];

function BasketTable() {
  const classes = useStyles();
  // const totalPrice = rows.map((row) => {
  //   return (totalPrice += row.price);
  // });

  // console.log(totalPrice);
  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="caption table">
        <TableHead>
          <TableRow className="basketTableCell">
            <TableCell>상품정보</TableCell>
            <TableCell align="right">상품금액</TableCell>
            <TableCell align="right">수량</TableCell>
            <TableCell align="right">배송비</TableCell>
            <TableCell align="center">선택</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.name}>
              <TableCell component="th" scope="row">
                {row.name}
              </TableCell>
              <TableCell align="right">{row.price}</TableCell>
              <TableCell align="right">{row.quantity}</TableCell>
              <TableCell align="right">{row.delivePrice}</TableCell>
              <TableCell align="right">{row.check}</TableCell>
            </TableRow>
          ))}
        </TableBody>
        <caption></caption>
      </Table>
    </TableContainer>
  );
}

export default BasketTable;
