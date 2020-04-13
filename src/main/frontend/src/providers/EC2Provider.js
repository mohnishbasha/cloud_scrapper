import API from './API';

class EC2Provider extends API {
  constructor() {
    super();
    this.resource = 'ec2s';
  }

  getEC2s() {
    return API.get(this.resource);
  }
}
export default new EC2Provider();
