import React from "react";
import Button from "@material-ui/core/Button";
import "../Sections/Navbar.css";
import { withRouter } from "react-router-dom";

function LeftMenu(props) {
  const onClickHandlerBasketPage = () => {
    props.history.push("/basket");
  };

  const onClickHandlerManagementPage = () => {
    props.history.push("/management");
  };

  const onClickHandlerShoplistPage = () => {
    props.history.push("/shoplist");
  };
  return (
    <div id="left-menu">
      <div className="buttonLayout">
        <Button onClick={onClickHandlerBasketPage}>
          <img
            className="buttonImg"
            alt="img loading err"
            src="img/shopping-cart.png"
          />
        </Button>
      </div>

      <div className="buttonLayout">
        <Button onClick={onClickHandlerManagementPage}>
          <img
            className="buttonImg"
            alt="img loading err"
            src="img/watering_can.png"
          />
        </Button>
      </div>

      <div className="buttonLayout">
        <Button onClick={onClickHandlerShoplistPage}>
          <img className="buttonImg" alt="img loading err" src="img/shop.png" />
        </Button>
      </div>
    </div>
  );
}

export default withRouter(LeftMenu);
