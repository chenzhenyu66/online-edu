import request from '@/utils/request'

export default{
    getBanners(){
        return request({
            url: `/eduservice/frontbanner/banner`,
            method: 'get'
        })
    }
}