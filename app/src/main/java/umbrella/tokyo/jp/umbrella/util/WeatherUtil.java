package umbrella.tokyo.jp.umbrella.util;

import android.content.Context;

import umbrella.tokyo.jp.umbrella.R;

/**
 * Created by mori on 4/17/16.
 *
 * openweathermap のdiscriptionをiconに直す
 * TODO:enumの使い方について.
 */
public class WeatherUtil {

    public static final String TAG = WeatherUtil.class.getSimpleName();

    public enum Description{
        CLEAR_SKY("clear sky"),
        FEW_CLOUDS("few clouds"),
        SCATTERED_CLOUDS("scatter clouds"),
        BROKEN_CLOUDS("broken clouds"),
        SHOWER_RAIN("shower rain"),
        RAIN("rain"),
        THUNDERSTORM("thunderstorm"),
        SNOW("snow"),
        MIST("mist");
        Description(String description){
            this.description = description;
        }
        private final String description;
        public static Description convey(String description){
            Description desc ;
            if(description.equals(CLEAR_SKY.description)){
                desc = CLEAR_SKY;
            }else if(description.equals(FEW_CLOUDS.description)){
                desc = FEW_CLOUDS;
            }else if(description.equals(SCATTERED_CLOUDS.description)){
                desc = SCATTERED_CLOUDS;
            }else if(description.equals(BROKEN_CLOUDS.description)){
                desc = BROKEN_CLOUDS;
            }else if(description.equals(SHOWER_RAIN.description)){
                desc = SHOWER_RAIN;
            }else if(description.equals(RAIN.description)){
                desc = RAIN;
            }else if(description.equals(THUNDERSTORM.description)){
                desc = THUNDERSTORM;
            }else if(description.equals(SNOW.description)){
                desc = SNOW;
            }else if(description.equals(MIST.description)){
                desc = MIST;
            }else {
                desc = null;
            }
            return desc;
        }
    }

    static public String getIconResource(Context context, Description description){
        int resourceId  = R.string.wi_wu_unknown;
        LogUtil.d(TAG,description.description);
        switch (description){
            case CLEAR_SKY:
                resourceId = R.string.wi_day_sunny;
                break;
            case FEW_CLOUDS:
                resourceId = R.string.wi_day_cloudy;
                break;
            case SCATTERED_CLOUDS:
                resourceId = R.string.wi_cloudy;
                break;
            case BROKEN_CLOUDS:
                resourceId =  R.string.wi_rain_mix;
                break;
            case SHOWER_RAIN:
                resourceId =  R.string.wi_rain_wind;
                break;
            case RAIN:
                resourceId =  R.string.wi_rain;
                break;
            case THUNDERSTORM:
                resourceId =  R.string.wi_thunderstorm;
                break;
            case SNOW:
                resourceId =  R.string.wi_snow;
                break;
            case MIST:
                resourceId =  R.string.wi_sprinkle;
                break;
        }
        return context.getString(resourceId);
    }

}
