import React from "react";
import "../BasketPage/BasketPage.css";
import { Card, Button, Grid } from "@material-ui/core";
import ShoppingCartIcon from "@material-ui/icons/ShoppingCart";
import BasketTable from "./BasketTable";
import { useMediaQuery } from "react-responsive";
import { withRouter } from "react-router-dom";

function BasketPage(props) {
  const onClickHandlerOderPage = () => {
    props.history.push("/oder");
  };

  const onClickHandlerLangdingPage = () => {
    props.history.push("/");
  };

  return (
    <div id="entire-BasketaPage">
      <Card className="card">
        <Grid className="grid">
          <div className="title">
            <ShoppingCartIcon style={{ fontSize: 50 }} />
            &nbsp;장바구니
          </div>
          <div className="category">
            &nbsp; 장바구니 {">"} 주문/결제 &gt; 주문완료
          </div>
          <div className="contents">
            <BasketTable />
          </div>
          <div className="price">총 가격 : 224,000원</div>
          <div className="button">
            <Button
              variant="contained"
              color="secondary"
              onClick={onClickHandlerOderPage}
            >
              <div className="btn-font">구매하기</div>
            </Button>
            <Button variant="outlined" color="primary">
              <div className="btn-font" onClick={onClickHandlerLangdingPage}>
                계속 쇼핑하기
              </div>
            </Button>
          </div>
        </Grid>
      </Card>
    </div>
  );
}

export default withRouter(BasketPage);
