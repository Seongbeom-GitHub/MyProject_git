import React, { useEffect, useState } from "react";
import "../DetailPage/DetailPage.css";
import { Grid, Divider, Typography, Button } from "@material-ui/core";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import { USER_SERVER } from "../../Config.js";
import axios from "axios";
// import { RemoveIcon, AddIcon } from "@material-ui/core/Icon";
import RemoveIcon from "@material-ui/icons/Remove";
import AddIcon from "@material-ui/icons/Add";

const settings = {
  dots: true,
  arrows: false,
  infinite: true,
  autoplay: true,
  speed: 700,
  centerMode: true,
  slidesToShow: 1,
  slidesToScroll: 0,
  centerPadding: "0px",
  centerMode: true,
};

function DetailPage(props) {
  const [Product, setProduct] = useState(0);

  const onClickHandlerOder = () => {
    props.history.push("/oder");
  };

  const onClickHandlerBasker = () => {
    props.history.push("/basket");
  };

  const getProduct = () => {
    axios
      .get(`${USER_SERVER}/goods/1?goods_id=1&category_id=1`)
      .then((response) => {
        if (response.data[0].hasOwnProperty("gid")) {
          setProduct(response.data[0]);
        } else {
          alert(" 상품들을 가져오는데 실패 했습니다.");
        }
      });
  };

  useEffect(() => {
    console.log("마운트 될때만 실행");
    getProduct();
  }, []);

  useEffect(() => {
    console.log("render 될때마다 실행");
  });

  return (
    <div id="entire-DetailPage">
      <div className="maingrid">
        <div className="category">상품&gt;&nbsp;꽃&gt;&nbsp;꽃다발</div>
        <div className="contents">
          <div className="gridIngrid">
            <div className="imgArea">
              <Slider {...settings} className="slider">
                <div className="slider_image_div">
                  <img
                    src="img/item-sample-img1.png"
                    alt="Image loading falied!"
                    className="slider_image"
                  />
                </div>

                <div className="slider_image_div">
                  <img
                    src="img/item-sample-img2.png"
                    alt="Image loading falied!"
                    className="slider_image"
                  />
                </div>
              </Slider>
            </div>
            <div className="detail">
              <div className="gridIngrid_2">
                <div className="itemName">{Product.name}</div>
                <Divider />
                <div className="itemPrice">가격 : {Product.price}</div>
                <div className="itemShipping">배송비 : 2500원</div>
                <div className="itemQuantity">
                  수량 :
                  <RemoveIcon /> &nbsp;{Product.discount}&nbsp; <AddIcon />
                </div>
                <div className="itemButton">
                  <Button
                    variant="contained"
                    color="secondary"
                    className="btnInDetailArea"
                    onClick={onClickHandlerOder}
                  >
                    바로구매
                  </Button>
                  <Button
                    variant="outlined"
                    color="primary"
                    className="btnInDetailArea"
                    onClick={onClickHandlerBasker}
                  >
                    장바구니
                  </Button>
                </div>
              </div>
            </div>
            <div className="desc">
              <img
                className="imgItemDetailsampleImg"
                alt="img loading err"
                src="img/imgItemDetailsampleImg.jpg"
              />
            </div>
            <div className="review">
              상품평
              <img src="img/rating.png" alt="Image loading falied!" />
            </div>

            <div className="footer">
              상호명 및 호스팅 서비스 제공 : Flower News
              <br />
              대표이사 / 사업자등록번호 / 통신판매업신고 / 고객센터
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default DetailPage;
