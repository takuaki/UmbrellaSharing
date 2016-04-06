package umbrella.tokyo.jp.umbrella.aws;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;

/**
 * Created by mori on 4/6/16.
 */
public class SNSMobilePush {

    private static final String IDENTITY_POOL = "ap-northeast-1:745759a9-a70f-4cdb-a824-836788ae56e1";
    private static final String APPLICATION_ARN ="arn:aws:sns:ap-northeast-1:986991677938:app/GCM/Umbrella";
    private static final String TOPIC_ARN = "arn:aws:sns:ap-northeast-1:986991677938:umbrella_share_android";
    private static final String TOKEN = "fJ-qqB6-2Ck:APA91bFv5KS2evO06olhoSAIFZLK8WkO1-cadWJ8VwY4n6ghQyzEKqc4HdD9fQjCwmp7MSEw628xHwV5lTeofDfYTstLG7RqZAnOHg-AB1IZerMPU3W_3qW2VG8lSFoQNXZLMyH9VYzx";

    private Context context;
    private AmazonSNSClient snsClient;
    private CognitoCachingCredentialsProvider credentialsProvider;

    //
    public SNSMobilePush(Context context) {
        this.context = context;
        credentialsProvider = new CognitoCachingCredentialsProvider(context, IDENTITY_POOL, Regions.AP_NORTHEAST_1);
        snsClient = new AmazonSNSClient(credentialsProvider);
        snsClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));

        CreatePlatformEndpointRequest createRequest = new CreatePlatformEndpointRequest();
        createRequest.setPlatformApplicationArn(APPLICATION_ARN);createRequest.setToken(TOKEN);
        CreatePlatformEndpointResult platformEndpoint = snsClient.createPlatformEndpoint(createRequest);

        String endpointArn = platformEndpoint.getEndpointArn();
        snsClient.subscribe(TOPIC_ARN,"application",endpointArn);
    }

    public void publish(String message){
        snsClient.publish(TOPIC_ARN,message);
    }


}
