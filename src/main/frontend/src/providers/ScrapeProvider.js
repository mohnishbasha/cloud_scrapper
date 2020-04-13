import API from './API';

class ScrapeProvider extends API {
  constructor() {
    super();
    this.resource = 'scrapes';
    this._resource = 'scrape';
  }

  getScrapes() {
    return API.get(this.resource);
  }

  createScrape(data) {
    return API.post(this.resource, data);
  }

  getEC2ByScrape(id) {
    return API.get(`${this._resource}/${id}/ec2s`);
  }
}
export default new ScrapeProvider();
