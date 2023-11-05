package view;

import java.awt.*;

import javax.swing.*;

import model.ROModel;

public class BasicReversiView extends JFrame implements ReversiView{
  private final ReversiPanel panel;

  public BasicReversiView(ROModel model) throws HeadlessException {
    this.panel = new ReversiPanel(model);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(panel); // Add the panel to the frame
    this.pack(); // Resize the frame to fit the panel
  }


  @Override
  public void addFeatureListener(ViewFeatures feature) {
    //TODO: Implement this using the panel's addFeatureListener method.
    this.panel.addFeaturesListener(feature);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  @Override
  public void advance() {
    //TODO: Implement this using the panel's advance method.
    this.panel.advance();
  }

  @Override
  public void error() {
    //TODO: Implement this using the panel's error method.
    this.panel.error();
  }
}
