console.log("Script loaded....");

let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

function getTheme() {
  const theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}

function setTheme(theme) {
  localStorage.setItem("theme", theme);
}

function changeTheme() {
  changePageTheme(currentTheme, currentTheme);
  document.querySelector("html").classList.add(currentTheme);

  const changeThemeButton = document.querySelector("#theme-change-button");

  changeThemeButton.addEventListener("click", () => {
    const oldTheme = currentTheme;
    console.log("change theme button clicked");

    currentTheme = currentTheme === "dark" ? "light" : "dark";

    changePageTheme(currentTheme, oldTheme);
  });
}

function changePageTheme(theme, oldTheme) {
  setTheme(theme);

  const html = document.querySelector("html");
  html.classList.remove(oldTheme);
  html.classList.add(theme);

  document
    .querySelector("#theme-change-button")
    .querySelector("span").textContent = theme === "light" ? "Dark" : "Light";
}
