import React from "react";
import { Typography } from "@material-ui/core";
import ShopCard from "./shopCard";
import "../ShoplistPage/shopPage.css";
import RoomIcon from "@material-ui/icons/Room";
import ThumbUpIcon from "@material-ui/icons/ThumbUp";
import { Grid, Divider } from "@material-ui/core";
import { withRouter } from "react-router-dom";

const cards1 = [1, 2, 3, 4, 5, 6, 7, 8];
const cards2 = [1, 2, 3, 4, 5, 6, 7, 8];

function ShoplistPage(props) {
  return (
    <div id="entire-ShoplistPage">
      <div>
        <div className="title">주변 상점들을 둘러보세요!</div>
      </div>
      <div className="div1">
        <RoomIcon />
        <div className="title-sub1">
          &nbsp;가까운 거리의 상점을 편하게 주문하세요
        </div>
      </div>

      <Grid container spacing={1}>
        <div className="gridShoplist">
          {cards1.map((shopCard) => (
            <Grid
              item
              key={shopCard}
              xs={12}
              sm={6}
              md={4}
              lg={3}
              className="alignList"
            >
              <ShopCard />
            </Grid>
          ))}
        </div>
      </Grid>

      <Divider />

      <div className="div2">
        <ThumbUpIcon />
        <div className="title-sub2">
          &nbsp;좋은 평점의 상점을 편하게 주문하세요
        </div>
      </div>

      <Grid container spacing={1}>
        <div className="gridShoplist">
          {cards2.map((shopCard) => (
            <Grid
              item
              key={shopCard}
              xs={12}
              sm={6}
              md={4}
              lg={3}
              className="alignList"
            >
              <ShopCard />
            </Grid>
          ))}
        </div>
      </Grid>
    </div>
  );
}

export default withRouter(ShoplistPage);
