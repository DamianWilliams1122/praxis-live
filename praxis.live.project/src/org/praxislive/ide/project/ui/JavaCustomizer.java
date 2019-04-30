/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019 Neil C Smith.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details.
 *
 * You should have received a copy of the GNU General Public License version 3
 * along with this work; if not, see http://www.gnu.org/licenses/
 *
 *
 * Please visit http://neilcsmith.net if you need additional information or
 * have any questions.
 */
package org.praxislive.ide.project.ui;

import java.util.Objects;
import org.praxislive.ide.project.DefaultPraxisProject;
import org.praxislive.ide.project.ProjectPropertiesImpl;
import static org.praxislive.ide.project.DefaultPraxisProject.MIN_JAVA_VERSION;
import static org.praxislive.ide.project.DefaultPraxisProject.MAX_JAVA_VERSION;

/**
 *
 * @author Neil C Smith (http://neilcsmith.net)
 */
final class JavaCustomizer extends javax.swing.JPanel {
    
    private static final String JVM_TEXT = 
        System.getProperty("java.version", "unknown") + "; " +
                System.getProperty("java.vm.name", "") + " " + 
                System.getProperty("java.vm.version", "");
    
    private final DefaultPraxisProject project;
    private final ProjectPropertiesImpl props;

    /**
     * Creates new form JavaCustomizer
     */
    JavaCustomizer(DefaultPraxisProject project) {
        this.project = Objects.requireNonNull(project);
        props = project.getLookup().lookup(ProjectPropertiesImpl.class);
        initComponents();
        runningJVMField.setText(JVM_TEXT);
        for (int i=MIN_JAVA_VERSION; i <= MAX_JAVA_VERSION; i++) {
            javaReleaseSelect.addItem("Java " + i);
        }
        refresh();
    }

    void refresh() {
        int projectRelease = props.getJavaRelease();
        int selectIndex = projectRelease - MIN_JAVA_VERSION;
        String selectValue = javaReleaseSelect.getItemAt(selectIndex);
        if (selectValue != null) {
            warningLabel.setVisible(false);
            javaReleaseSelect.setSelectedIndex(selectIndex);
        } else {
            warningLabel.setVisible(true);
            javaReleaseSelect.setSelectedIndex(javaReleaseSelect.getItemCount() - 1);
        }
        javaReleaseSelect.setEnabled(!project.isActive());
    }
    
    void updateProject() {
        int selected = javaReleaseSelect.getSelectedIndex();
        if (selected < 0) {
            return;
        }
        int projectRelease = selected + MIN_JAVA_VERSION;
        props.setJavaRelease(projectRelease);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javaReleaseLabel = new javax.swing.JLabel();
        javaReleaseSelect = new javax.swing.JComboBox<>();
        runningJVMLabel = new javax.swing.JLabel();
        runningJVMField = new javax.swing.JLabel();
        warningLabel = new javax.swing.JLabel();

        javaReleaseLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        javaReleaseLabel.setText(org.openide.util.NbBundle.getMessage(JavaCustomizer.class, "JavaCustomizer.javaReleaseLabel.text")); // NOI18N

        runningJVMLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        runningJVMLabel.setText(org.openide.util.NbBundle.getMessage(JavaCustomizer.class, "JavaCustomizer.runningJVMLabel.text")); // NOI18N

        runningJVMField.setText(org.openide.util.NbBundle.getMessage(JavaCustomizer.class, "JavaCustomizer.runningJVMField.text")); // NOI18N

        warningLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.errorForeground"));
        warningLabel.setText(org.openide.util.NbBundle.getMessage(JavaCustomizer.class, "JavaCustomizer.warningLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(javaReleaseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(runningJVMLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(warningLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))
                    .addComponent(javaReleaseSelect, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(runningJVMField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(javaReleaseSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(javaReleaseLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runningJVMLabel)
                    .addComponent(runningJVMField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warningLabel)
                .addContainerGap(208, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel javaReleaseLabel;
    private javax.swing.JComboBox<String> javaReleaseSelect;
    private javax.swing.JLabel runningJVMField;
    private javax.swing.JLabel runningJVMLabel;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables


}
