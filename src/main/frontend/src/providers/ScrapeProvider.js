import API from './API';

class ScrapeProvider extends API {
  constructor() {
    super();
    this.resources = 'scrapes';
    this.resource = 'scrape';
  }

  getScrapes() {
    return API.get(this.resources);
  }

  createScrape(data) {
    return API.post(this.resources, data);
  }

  getEC2ByScrape(id) {
    return API.get(`${this.resource}/${id}/ec2s`);
  }
}
export default new ScrapeProvider();
