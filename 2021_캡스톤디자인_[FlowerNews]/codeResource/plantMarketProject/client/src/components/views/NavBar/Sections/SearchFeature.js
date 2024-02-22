import React from "react";
import { alpha, makeStyles } from "@material-ui/core/styles";
import InputBase from "@material-ui/core/InputBase";
import SearchIcon from "@material-ui/icons/Search";

const useStyles = makeStyles((theme) => ({
  search: {
    padding: 3,
    justifyContent: "space-between",
    width: "100%",
    height: 30,
    display: "flex",
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.black, 0.15),
    "&:hover": {
      backgroundColor: alpha(theme.palette.common.black, 0.25),
    },
  },
  searchIcon: {
    padding: theme.spacing(0, 2),
    height: "100%",
    pointerEvents: "Button",
    alignItem: "center",
  },
  inputRoot: {
    color: "inherit",
    display: "flex",
    padding: 3,
  },
  inputInput: {
    // padding: theme.spacing(0),
    // // vertical padding + font size from searchIcon
    // paddingLeft: `calc(1em + ${theme.spacing(4)}px)`,
    // transition: theme.transitions.create("width"),
    // width: "100%",
    // [theme.breakpoints.up("md")]: {
    //   width: "50ch",
    // },
  },
}));

function SearchReature(props) {
  const classes = useStyles();

  return (
    <div className={classes.search}>
      <InputBase
        placeholder="상품 검색"
        classes={{
          root: classes.inputRoot,
          input: classes.inputInput,
        }}
        inputProps={{ "aria-label": "search" }}
      />
      <div className={classes.searchIcon}>
        <SearchIcon />
      </div>
    </div>
  );
}

export default SearchReature;
