import React, { useState, useEffect } from "react";
import "./LandingPage.css";
import "../../Fonts.css";
import { Typography, makeStyles } from "@material-ui/core";
// import ShoppingBasketIcon from "@material-ui/icons/ShoppingBasket";
// import SearchIcon from "@material-ui/icons/Search";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from "react-slick";

import MerchandiseList from "./MerchandiseList";
import StorefrontIcon from "@material-ui/icons/Storefront";

const useStyles = makeStyles((theme) => ({
  icon: {
    marginRight: theme.spacing(2),
  },
  heroContent: {
    backgroundColor: theme.palette.background.paper,
    padding: theme.spacing(8, 0, 6),
  },
}));

// 광고 배너 슬라이드 셋팅
const settings = {
  dots: true,
  infinite: true,
  autoplay: true,
  speed: 500,
  centerMode: true,
  slidesToShow: 1,
  slidesToScroll: 1,
  centerPadding: "0px",
  centerMode: true,
};

function LandingPage(props) {
  const classes = useStyles();

  const onClickHandlerShoplist = () => {
    props.history.push("/shoplist");
  };

  const onClickHandlerDetail = () => {
    props.history.push("/detail");
  };

  return (
    <div id="entire-LandingPage">
      {/* Hero unit */}
      <div className={classes.heroContent}>
        <div className="intro">
          <img
            className="main_intro_img"
            alt="img loading err"
            src="img/main_flowerImg.jpg"
          />

          <Typography
            component="h1"
            variant="h2"
            align="center"
            gutterBottom
            className="title-Name"
          >
            Flower News
          </Typography>
          <div className="text">
            당신의 소중한 마음을 담아, 소중한 사람에게 전해주세요
          </div>
        </div>
      </div>

      <div className="add-banner" onClick={onClickHandlerDetail}>
        <div className="banner-phrase">다양한 이벤트를 놓치지 마세요!</div>

        <Slider {...settings}>
          <div className="slider_image_div2">
            <img
              src="img/add-banner-img.png"
              alt="Image loading falied!"
              className="slider_image2"
            ></img>
          </div>
          <div className="slider_image_div2">
            <img
              src="img/add-banner-img2.png"
              alt="Image loading falied!"
              className="slider_image2"
            ></img>
          </div>
        </Slider>
      </div>

      <MerchandiseList />

      <div className="store" onClick={onClickHandlerShoplist}>
        <img
          className="imgShop"
          alt="img loading err"
          src="img/imgFlowerShop.jpg"
        />
        <StorefrontIcon className="title-Name-icon" />
        <Typography
          component="h1"
          variant="h2"
          align="center"
          gutterBottom
          className="title-Name"
        >
          Shop
        </Typography>
        <div className="text">주변 꽃가게 눌러서 바로가기</div>
      </div>
    </div>
  );
}

export default LandingPage;
