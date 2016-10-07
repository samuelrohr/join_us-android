package InternalStorage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Rohr on 10/6/2016.
 */
public class InternalStorageManager {

    /**
     * Salva no internal store uma string recebida.
     * @param context
     * @param fileName
     * @param data
     * @return retorna true para sucesso e false para erro
     */
    public static Boolean writeData(Context context, String fileName, String data) {
        try {
            //Salva o json para usar em offline
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();

            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Le o conteúdo de um arquivo interno
     * @param fileName
     * @param context
     * @return retorno uma String ou "" em caso de erro
     */
    public static String readData(String fileName, Context context) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

}
