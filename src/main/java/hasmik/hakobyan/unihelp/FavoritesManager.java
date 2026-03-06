package hasmik.hakobyan.unihelp; // Убедитесь, что пакет совпадает

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class FavoritesManager {
    private static final String PREF_NAME = "unihelp_prefs";
    private static final String KEY_LIKES = "liked_ids";

    // 1. Сохраняем лайк (добавляем или удаляем ID из списка)
    public static void saveLike(Context context, int itemId, boolean isLiked) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // Получаем текущий список ID (обязательно создаем копию через new HashSet)
        Set<String> likedIds = new HashSet<>(prefs.getStringSet(KEY_LIKES, new HashSet<>()));

        if (isLiked) {
            likedIds.add(String.valueOf(itemId)); // Добавляем ID как строку
        } else {
            likedIds.remove(String.valueOf(itemId)); // Удаляем ID
        }

        // Сохраняем обновленный список обратно в память
        prefs.edit().putStringSet(KEY_LIKES, likedIds).apply();
    }

    // 2. Получаем список всех лайкнутых ID
    public static Set<String> getLikedIds(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(KEY_LIKES, new HashSet<>());
    }
}