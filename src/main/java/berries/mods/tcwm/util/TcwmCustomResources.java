package berries.mods.tcwm.util;

public class TcwmCustomResources {
  /*
  public static Map<String, CustomPlayerSounds> CUSTOM_SOUNDS = new HashMap();
  private static final JsonParser JSON_PARSER = new JsonParser();

  public static void reload(ResourceManager manager) {
    try {
      TcwmCustomResources.readResource(manager, "tcwm:tcwm_custom_resources.json", (config) -> {
        try{
          config.get("custom_sounds").getAsJsonObject().entrySet().forEach((entry) -> {
            try{
              JsonObject jsonObject = ((JsonElement)entry.getValue()).getAsJsonObject();
              String name = (String)getOrDefault(jsonObject, "name", entry.getKey(), JsonElement::getAsString);
              SoundEvent soundEvent = RegistryUtilities.createSoundEvent(new Identifier((String)getOrDefault(jsonObject, "soundID", entry.getKey(), JsonElement::getAsString)));
              int volume = (Integer)getOrDefault(jsonObject, "volume", 100, JsonElement::getAsInt);
              String color = (String)getOrDefault(jsonObject, "color", "#FFFFFF", JsonElement::getAsString);
              CUSTOM_SOUNDS.put("tcwm_player_sound_" + entry.getKey(), new CustomPlayerSounds(name, soundEvent, volume, colorStringToInt(color)));
            }
            catch(Exception e){
              e.printStackTrace();
            }
          });
        }
        catch (Exception e){
          e.printStackTrace();
        }
      });
    }
    catch (IOException k){
      k.printStackTrace();
      System.out.println("失败了！");
    }
    catch (NullPointerException j){
      j.printStackTrace();
      System.out.println("失败了！");
    }

    System.out.println("Loaded " + CUSTOM_SOUNDS.size() + " custom sound(s)");
    Set s = CUSTOM_SOUNDS.keySet();
    PrintStream stream = System.out;
    Objects.requireNonNull(stream);
    s.forEach(stream::println);
  }

  public static int colorStringToInt(String string) {
    try {
      return Integer.parseInt(string.toUpperCase(Locale.ENGLISH).replaceAll("[^\\dA-F]", ""), 16);
    } catch (Exception var2) {
      return 0;
    }
  }

  private static void readResource(ResourceManager manager, String path, Consumer<JsonObject> callback) throws IOException {
    try {
      UtilitiesClient.getResources(manager, new Identifier(path)).forEach((resource) -> {
        try {
          InputStream stream = Utilities.getInputStream(resource);

          try {
            callback.accept((new JsonParser()).parse(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject());
          } catch (Throwable var7) {
            if (stream != null) {
              try {
                stream.close();
              } catch (Throwable var6) {
                var7.addSuppressed(var6);
              }
            }

            throw var7;
          }

          if (stream != null) {
            stream.close();
          }
        } catch (Exception var8) {
          var8.printStackTrace();
        }

        try {
          Utilities.closeResource(resource);
        } catch (IOException var5) {
          var5.printStackTrace();
        }

      });
    } catch (Exception var4) {
    }

  }

  private static <T> T getOrDefault(JsonObject jsonObject, String key, T defaultValue, Function<JsonElement, T> function) {
    return jsonObject.has(key) ? function.apply(jsonObject.get(key)) : defaultValue;
  }

  public static class CustomPlayerSounds{
    public final String name;
    public final SoundEvent soundEvent;
    public final int volume;
    public final int color;

    public CustomPlayerSounds(String name, SoundEvent soundEvent, int volume, int color){
      this.name = name;
      this.soundEvent = soundEvent;
      this.volume = volume;
      this.color = color;
    }
  }

   */
}
