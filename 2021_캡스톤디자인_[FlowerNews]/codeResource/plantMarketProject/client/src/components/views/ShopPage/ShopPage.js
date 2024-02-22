import React from "react";
import "./ShopPage.css";
import MerchandiseList from "../LandingPage/MerchandiseList.js";

function ShopPage() {
  return (
    <div id="entire-ShopPage">
      <div className="main-grid">
        <div className="shopImg">
          <img alt="" src="img/shopImg_sample.png" className="shopImgSize" />
        </div>
        <div className="shopIntro">
          <div className="shopName">HAPPY FLOWER</div>
          <div className="shopIntro">
            축하의 자리, 축하하고 싶은날 자리를 빛내주세요! 기쁨이 두배가 됩니다
            <br />
            <br />
            위치 : 서울특별시 서초구 서초동 1715-6
          </div>
        </div>
        <div className="contents">
          <MerchandiseList />
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

export default ShopPage;
