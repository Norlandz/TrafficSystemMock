package com.redfrog.trafficsm.randomtest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.redfrog.trafficsm.test.javafxBoot.JfxAppSimpleSetup;
import com.redfrog.trafficsm.util.JavafxUtil;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

class JavafxImageSnapshotTest extends ApplicationTest {

  private AnchorPane panel_SemanticRoot;

  @Override
  public void start(Stage primaryStage) { panel_SemanticRoot = JfxAppSimpleSetup.startSetup(primaryStage); }

  @Nested
  class TestNest01 {

    @Test
    public void image_snapshot_file_format() {
      JavafxUtil.javafx_platform_runLater_sync(() -> {
        Circle circle = new Circle(200, 200, 100, JavafxUtil.color_Yellow);
        panel_SemanticRoot.getChildren().add(circle);
      });

      //_________

      //___
      //___________________________________________________________________
      //___
      //_________________________________________________________________
      //_
      //___
      //__________________________________________________________________________________________________________________________________
      //___
      //_________________________________________________________________________________________
      //_

      SnapshotParameters snapshotParameters = new SnapshotParameters();
      snapshotParameters.setFill(Color.TRANSPARENT);
      //
      FutureTask<WritableImage> future = new FutureTask<>(() -> {
        return panel_SemanticRoot.snapshot(snapshotParameters, null);
      });
      Platform.runLater(future); //________________________
      WritableImage writableImage;
      try {
        writableImage = future.get();
      } catch (InterruptedException e) {
        throw new Error(e);
      } catch (ExecutionException e) {
        throw new Error(e);
      }
      //
      BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

      ByteArrayOutputStream byteArrayOutputStream_svg = new ByteArrayOutputStream();
      ByteArrayOutputStream byteArrayOutputStream_png = new ByteArrayOutputStream();
      try {
        //_______________________________________________________________________
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream_png);
      } catch (IOException e) {
        throw new Error(e);
      }
      //__________________________________________________________________
      byte[] result_png = byteArrayOutputStream_png.toByteArray();
      //_________________________________________________
      System.out.println(new String(result_png));
      System.out.println(Base64.getEncoder().encodeToString(result_png));

      sleep(999999);
    }

  }

}
