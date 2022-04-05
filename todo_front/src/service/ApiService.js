import { API_BASE_URL } from "../app-config";
const ACCESS_TOKEN = "ACCESS_TOKEN";

export function call(api, method, request) {
  let headers = new Headers({
    "Content-Type": "application/json",
  });

  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken !== null) {
    headers.append("Authorization", "Bearer " + accessToken);
  }

  let options = {
    headers,
    url: API_BASE_URL + api,
    method: method,
  };
  if (request) {
    options.body = JSON.stringify(request);
  }
  console.log("options:", options);
  return fetch(options.url, options)
    .then((response) => response.json())
    .then((json) => {
      console.log("json:", json);
      if (json.error) {
        return Promise.reject(json);
      }
      console.log("success, json:", json);
      return json;
    })
    .catch((error) => {
      console.log("Error:", error);
      return Promise.reject(error);
    });
}

export function signin(userDto) {
  return call("/auth/signin", "POST", userDto).then((response) => {
    if (response.token) {
      localStorage.setItem("ACCESS_TOKEN", response.token);
      window.location.href = "/";
    }
  });
}

export function signout() {
  localStorage.setItem(ACCESS_TOKEN, null);
  window.location.href = "/login";
}

export function signup(userDto) {
  return call("/auth/signup", "POST", userDto);
}
