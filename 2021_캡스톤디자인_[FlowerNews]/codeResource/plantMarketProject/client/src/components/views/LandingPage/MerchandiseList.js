import * as React from "react";
import { Box, ImageList, ImageListItem, Masonry } from "@material-ui/core";
import "./MerchandiseList.css";
import { withRouter } from "react-router-dom";

const itemData = [
  {
    img: "img/1.jpg",
    title: "Bed",
  },
  {
    img: "img/4.jpg",
    title: "Books",
  },
  {
    img: "https://images.unsplash.com/photo-1523413651479-597eb2da0ad6",
    title: "Sink",
  },
  {
    img: "https://images.unsplash.com/photo-1563298723-dcfebaa392e3",
    title: "Kitchen",
  },
  {
    img: "https://images.unsplash.com/photo-1588436706487-9d55d73a39e3",
    title: "Blinds",
  },
  {
    img: "img/2.jpg",
    title: "Chairs",
  },
  {
    img: "img/5.jpg",
    title: "Laptop",
  },
  {
    img: "img/6.jpg",
    title: "Doors",
  },
  {
    img: "https://images.unsplash.com/photo-1517487881594-2787fef5ebf7",
    title: "Coffee",
  },
  {
    img: "https://images.unsplash.com/photo-1516455207990-7a41ce80f7ee",
    title: "Storage",
  },
  {
    img: "img/3.jpg",
    title: "Candle",
  },
  {
    img: "https://images.unsplash.com/photo-1519710164239-da123dc03ef4",
    title: "Coffee table",
  },
];

function MerchandiseList(props) {
  const onClickHandlerDetail = () => {
    props.history.push("/detail");
  };

  return (
    <div id="entire-MerchandiseList">
      <Box sx={{ overflowY: "scroll" }} className="box">
        <ImageList variant="masonry" cols={4} gap={8}>
          {itemData.map((item) => (
            <ImageListItem key={item.img}>
              <div onClick={onClickHandlerDetail}>
                <img
                  src={`${item.img}?w=248&fit=crop&auto=format`}
                  srcSet={`${item.img}?w=248&fit=crop&auto=format&dpr=2 2x`}
                  alt={item.title}
                  loading="lazy"
                  className="masonry-img"
                />
              </div>
            </ImageListItem>
          ))}
        </ImageList>
      </Box>
    </div>
  );
}

export default withRouter(MerchandiseList);
