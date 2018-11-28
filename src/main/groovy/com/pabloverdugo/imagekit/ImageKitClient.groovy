package com.pabloverdugo.imagekit

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import org.json.JSONArray
import org.json.JSONObject

class ImageKitClient {

    Properties properties = new Properties()

    String PUBLIC_API_KEY
    String PRIVATE_API_KEY
    String API_ID
    private final static String IMAGE_KIT_IMAGE_UPLOAD_URL = 'https://upload.imagekit.io/rest/api/image/v2/'
    private final static String IMAGE_KIT_ADMIN_URL = 'https://imagekit.io/api/admin/'

    ImageKitClient() {
        properties.load(ImageKitClient.class.getResourceAsStream('/imagekit.properties'))
        this.PUBLIC_API_KEY = properties.get('public_api_key')
        this.PRIVATE_API_KEY = properties.get('private_api_key')
        this.API_ID = properties.get('api_id')
    }

    JSONArray getLibrary(Integer skip) {
        def jsonResult = new JSONArray()
        if (!skip) {
            skip = 0
        }
        String content = "imagekitId=$API_ID&limit=1000&skip=$skip".toString()
        try {
            def uploadResponse = Unirest.get(
                    IMAGE_KIT_ADMIN_URL + 'media/listFiles?skip={skip}&limit={limit}&imagekitId={imagekitId}&signature={signature}')
                    .routeParam('skip', skip as String)
                    .routeParam('limit', '1000')
                    .routeParam('imagekitId', API_ID)
                    .routeParam('signature', Utils.sign(content, PRIVATE_API_KEY))
                    .asJson()
            jsonResult = uploadResponse.getBody().getArray()
        } catch (UnirestException e) {
            e.printStackTrace()
        }
        jsonResult
    }

    JSONObject getFileByName(String name) {
        def result = new JSONObject()
        def found = false
        def page = 0
        while (!found) {
            def pageResults = getLibrary(page * 1000)
            pageResults.each {
                if (it['name'] == name) {
                    result = it
                    found = true
                } else if (pageResults.size() != 1000) {
                    found = true
                } else {
                    page++
                }
            }
        }
        result
    }

    JSONObject uploadFile(String imagePath) {
        def jsonResult = new JSONObject()
        String filename = new File(imagePath).getName().toString()
        String time = Utils.timestamp()
        String content = "apiKey=" + PUBLIC_API_KEY + "&filename=" + filename + "&timestamp=" + time
        String sig = Utils.sign(content, PRIVATE_API_KEY)
        try {
            HttpResponse<JsonNode> uploadResponse = Unirest.post(IMAGE_KIT_IMAGE_UPLOAD_URL + API_ID)
                    .header("accept", "application/json")
                    .field("file", new File(imagePath))
                    .field("filename", filename)
                    .field("apiKey", PUBLIC_API_KEY)
                    .field("signature", sig)
                    .field("timestamp", time)
                    .asJson()
            jsonResult = uploadResponse.getBody().getObject()
        } catch (UnirestException e) {
            e.printStackTrace()
        }
        jsonResult
    }

    String cropImage(String imageUrl, Integer width, Integer height, Integer quality = null) {
        if (!imageUrl || !width || !height) {
            ''
        } else {
            def transformation = "/tr:h-$height,w-$width"
            if (quality) {
                transformation += ",q-$quality"
            }
            return imageUrl.replace(API_ID, API_ID + transformation.toString())
        }
    }

    String imageQuality(String imageUrl, Integer quality) {
        if (!imageUrl || !quality) {
            ''
        } else {
            def transformation = "/tr:q-$quality"
            return imageUrl.replace(API_ID, API_ID + transformation.toString())
        }
    }

}