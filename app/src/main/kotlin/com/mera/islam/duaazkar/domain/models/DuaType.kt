package  com.mera.islam.duaazkar.domain.models

import androidx.annotation.DrawableRes
import com.mera.islam.duaazkar.R

enum class DuaType(val type: Int,@DrawableRes val icon: Int) {
    ALL(0, R.drawable.ic_all_prayers_icon),
    Morning_Evening_Night(1, R.drawable.ic_all_prayers_icon),
    Wearing(2, R.drawable.ic_all_prayers_icon),
    Using_Bathroom(3, R.drawable.ic_all_prayers_icon),
    Home(4, R.drawable.ic_all_prayers_icon),
    Mosque(5, R.drawable.ic_mosque_icon),
    Ibadah(6, R.drawable.ic_all_prayers_icon),
    Decision_Making(7, R.drawable.ic_all_prayers_icon),
    Feelings(8, R.drawable.ic_all_prayers_icon),
    Doubt_in_Faith(9, R.drawable.ic_all_prayers_icon),
    Debt(10, R.drawable.ic_all_prayers_icon),
    Sin(11, R.drawable.ic_all_prayers_icon),
    Expelling_the_devil_and_his_whisperings(12, R.drawable.ic_all_prayers_icon),
    Mishap(13, R.drawable.ic_all_prayers_icon),
    Children(14, R.drawable.ic_all_prayers_icon),
    Sickness(15, R.drawable.ic_all_prayers_icon),
    Death(16, R.drawable.ic_all_prayers_icon),
    Weather(17, R.drawable.ic_all_prayers_icon),
    Sighting_the_crescent_moon(18, R.drawable.ic_all_prayers_icon),
    Eating(19, R.drawable.ic_all_prayers_icon),
    Guest(20, R.drawable.ic_all_prayers_icon),
    Seeing_premature_fruit(21, R.drawable.ic_all_prayers_icon),
    Marriage(22, R.drawable.ic_all_prayers_icon),
    Someone_in_trial(23, R.drawable.ic_all_prayers_icon),
    Gathering(24, R.drawable.ic_all_prayers_icon),
    Returning_a_supplication(25, R.drawable.ic_all_prayers_icon),
    One_who_does_you_a_favour(26, R.drawable.ic_all_prayers_icon),
    Protection_from_the_Dajjal(27, R.drawable.ic_all_prayers_icon),
    One_who_pronounces_his_love_for_you(28, R.drawable.ic_all_prayers_icon),
    One_who_has_offered_you_some_of_his_wealth(29, R.drawable.ic_all_prayers_icon),
    Fear_of_shirk(30, R.drawable.ic_all_prayers_icon),
    Someone_who_says_May_Allah_bless_you(31, R.drawable.ic_all_prayers_icon),
    Ascribing_things_to_evil_omens(32, R.drawable.ic_all_prayers_icon),
    Traveling(33, R.drawable.ic_travel_icon),
    Recieving_good_or_bad_news(34, R.drawable.ic_all_prayers_icon),
    Sending_prayers_upon_prophet_ﷺ(35, R.drawable.ic_all_prayers_icon),
    Greetings(36, R.drawable.ic_all_prayers_icon),
    Sounds_of_animals(37, R.drawable.ic_all_prayers_icon),
    Praise(38, R.drawable.ic_all_prayers_icon),
    News(39, R.drawable.ic_all_prayers_icon),
    Evil_eye(40, R.drawable.ic_all_prayers_icon),
    Slaughter_Sacrifce(41, R.drawable.ic_all_prayers_icon),
    Devils(42, R.drawable.ic_all_prayers_icon),
    Forgiveness(43, R.drawable.ic_all_prayers_icon),
    Tasbih(44, R.drawable.ic_all_prayers_icon),
    Good_Manners(45, R.drawable.ic_all_prayers_icon);


    fun getName() = name.replace("_", " ")

    companion object {
        fun toDuaType(type: Int): DuaType {
            var dua = Morning_Evening_Night

            for (duaType in values()) {
                if (duaType.type == type) {
                    dua = duaType
                    break
                }
            }

            return dua
        }
    }
}