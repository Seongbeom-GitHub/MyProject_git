import React from "react";
import "./ManagementPage.css";
import {
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Button,
  Typography,
} from "@material-ui/core";

function ManagementPage() {
  return (
    <div id="entire-ManagementPage">
      <div className="main-grid">
        <div className="intro2">나의 식물관리</div>
        <div className="contents">
          <div className="plantsCard">
            <Card sx={{ maxWidth: 345 }}>
              <CardMedia
                component="img"
                height="300px"
                image="img/imgMyPlantsSample.png"
              />
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  스파티필럼
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  2021-10-24
                  <br />
                  벤젠, 포르알데히드, 암모니아와 같은 다양한 공기 오염물질 제거
                  능력이 탁월하며, 음이온 발생량이 많다.
                </Typography>
              </CardContent>
              <CardActions>
                <Button size="small">물 주기</Button>
                <Button size="small">주문매장</Button>
              </CardActions>
            </Card>
          </div>

          <div className="plantsCard">
            <Card sx={{ maxWidth: 345 }}>
              <CardMedia component="img" height="300px" image="img/tree1.jpg" />
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  뱅갈고무나무
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  2021-07-24
                  <br />
                  공기정화 식물 중 하나로 폼알데하이드 제거에 탁월하고 음이온을
                  생성하며 악취제거, 오염물질 청정 기능을 합니다.
                </Typography>
              </CardContent>
              <CardActions>
                <Button size="small">물 주기</Button>
                <Button size="small">주문매장</Button>
              </CardActions>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ManagementPage;
