/*
 * Copyright (C) 2009 The Algorithm Study Project
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2.0 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License version
 * 2.0 along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 * or find it on the GNU website at:
 *
 *    http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
package com.algorithmstudy.visualizer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AlgorithmVisualizer implements EntryPoint {

  private static String STYLE_TOP_BARS = "top-bars";
  private static String STYLE_CONTENTS = "contents";
  private static String STYLE_SUPER_CONTAINER = "super-container";

  protected VerticalPanel contentsPanel;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    Label programName = new Label("Algorithm Visualizer");
    HTML algstudyDotCom = new HTML();
    algstudyDotCom.setHTML("<a href='http://www.algorithmstudy.com'>AlgorithmStudy.com</a>");

    HorizontalPanel titleBarPanel = new HorizontalPanel();
    titleBarPanel.setStyleName(STYLE_TOP_BARS);
    titleBarPanel.setWidth("100%");
    titleBarPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
    titleBarPanel.add(programName);
    titleBarPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
    titleBarPanel.add(algstudyDotCom);

    contentsPanel = new VerticalPanel();
    contentsPanel.setStyleName(STYLE_CONTENTS);
    contentsPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
    contentsPanel.setWidth("100%");

    Panel algorithmSelectionPanel = new AlgorithmSelectionPanel(contentsPanel);
    algorithmSelectionPanel.setStyleName(STYLE_TOP_BARS);
    algorithmSelectionPanel.setWidth("100%");

    VerticalPanel containingPanel = new VerticalPanel();
    containingPanel.setWidth("850px");
    containingPanel.setStyleName(STYLE_SUPER_CONTAINER);
    containingPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    containingPanel.add(titleBarPanel);
    containingPanel.add(algorithmSelectionPanel);
    containingPanel.add(contentsPanel);

    RootPanel.get().add(containingPanel);
  }

}
