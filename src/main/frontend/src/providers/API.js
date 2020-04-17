import axios from 'axios';

class API {
  static createUrl(path, options = {}) {
    const queryParams = [];

    if (options.limit) {
      queryParams.push(`limit=${options.limit}`);
    }

    const queryString = queryParams.join('&');
    const withQueryString = queryString === '' ? `/${path}` : `/${path}?${queryString}`;
    return `/api${withQueryString}`;
  }

  static getHeaders() {
    return { headers: { 'Content-Type': 'application/json' } };
  }

  static get(path, id, options = {}) {
    const withId = id ? `/${id}` : '';
    const url = API.createUrl(path + withId, options);
    return axios.get(url, API.getHeaders());
  }

  static post(path, data = {}) {
    const url = API.createUrl(path, {});
    return axios.post(url, data, API.getHeaders());
  }

  static logout() {
    axios.post('/logout').then((resp) => {
      window.location = resp.data.redirect;
    });
  }
}

export default API;
