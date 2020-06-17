const baseUrl = '';
const timeoutSeconds = 20;

export default class BaseRequest{

    static post(url,params){
        let p1 = new Promise((resolve,reject)=>{
            fetch(url,{
                method:'POST',
                headers:{
                    'Accept':'application/json',
                    'Content-Type':'application/json',
                },
                body:JSON.stringify(params)
            })
        })
        .then((respone)=>response.json())
        .then((responseJson)=>{
        /// 拿到数据可以在此同意处理服务器返回的信息
            resolve(responseJson);
         })
         .catch((error)=>{
            reject(error);
         })

         let p2 = this.requestTimeout();
         return Promise.race([p1,p2])

    }

    /// Get方法
    static getData(url,parame){
        let p1= new Promise((resolve,rejcet)=>{
            fetch(url)
            .then((response)=>response.json())
            .then((responseJson)=>{
                /// 拿到数据可以在此同意处理服务器返回的信息
                resolve(responseJson);
            })
            .catch((error)=>{
                reject(error);
            })
        })
        let p2 = this.requestTimeout();
        return Promise.race([p1,p2]);
    }


     /// 设置超时的方法
    static requestTimeout(){
        return new Promise((resolve,reject)=>{
            setTimeout(()=>{
                reject('链接超时');
            },timeoutSeconds * 1000)
        })
    }

}