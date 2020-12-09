/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020 Neil C Smith.
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

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.ListSelectionModel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.praxislive.ide.project.DefaultPraxisProject;
import org.praxislive.ide.project.api.ExecutionLevel;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.ListView;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.praxislive.ide.project.api.ExecutionElement;
import org.praxislive.ide.project.api.ProjectProperties;

/**
 *
 */
@NbBundle.Messages({
    "LBL_addFile=Add file",
    "LBL_add=Add",
    "LBL_addLine=Add line",
    "LBL_enterInputLine=Enter script line",
    "ERR_invalidLine=Invalid script line"
})
class ElementsCustomizer extends javax.swing.JPanel implements ExplorerManager.Provider {

    private ExplorerManager manager;
    private DefaultPraxisProject project;
    private ExecutionLevel level;
    private ProjectProperties props;
    private List<ExecutionElement> elements;
    private Elements children;
    private Node root;

    /**
     * Creates new form FilesCustomizer
     */
    ElementsCustomizer(DefaultPraxisProject project, ExecutionLevel level) {
        if (project == null || level == null) {
            throw new NullPointerException();
        }
        this.project = project;
        this.level = level;
        props = project.getLookup().lookup(ProjectProperties.class);
        manager = new ExplorerManager();
        elements = new ArrayList<>();
        children = new Elements();
        root = new AbstractNode(children);
        refreshList();
        manager.setRootContext(root);
        manager.addPropertyChangeListener(new ManagerListener());
        initComponents();
        ((ListView) fileList).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    final void refreshList() {
        elements.clear();
        if (props != null) {
            elements.addAll(props.getElements(level));
        }
        refreshView();
    }

    private void refreshView() {
        children.setElements(elements);
    }

    List<ExecutionElement> getElements() {
        return List.copyOf(elements);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileList = new ListView();
        addFileButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        addLineButton = new javax.swing.JButton();

        addFileButton.setText(org.openide.util.NbBundle.getMessage(ElementsCustomizer.class, "ElementsCustomizer.addFileButton.text")); // NOI18N
        addFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileButtonActionPerformed(evt);
            }
        });

        removeButton.setText(org.openide.util.NbBundle.getMessage(ElementsCustomizer.class, "ElementsCustomizer.removeButton.text")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        addLineButton.setText(org.openide.util.NbBundle.getMessage(ElementsCustomizer.class, "ElementsCustomizer.addLineButton.text")); // NOI18N
        addLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLineButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fileList, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addLineButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileList, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addFileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addLineButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileButtonActionPerformed
        FileChooserBuilder fcb = new FileChooserBuilder(ElementsCustomizer.class); // use project specific String?
        fcb.setFilesOnly(true);
        fcb.setTitle(Bundle.LBL_addFile());
        fcb.setApproveText(Bundle.LBL_add());
        fcb.setDefaultWorkingDirectory(FileUtil.toFile(project.getProjectDirectory()));
        fcb.forceUseOfDefaultWorkingDirectory(true);
        File add = fcb.showOpenDialog();
        if (add != null) {
            FileObject file = FileUtil.toFileObject(add);
            if (validateFile(file)) {
                elements.add(ExecutionElement.forFile(file));
                refreshView();
            }
        }
    }//GEN-LAST:event_addFileButtonActionPerformed

    private boolean validateFile(FileObject file) {
        return FileUtil.isParentOf(project.getProjectDirectory(), file);
//        && !isConfigFile(file);
    }

    private boolean isConfigFile(FileObject file) {
        FileObject configDir = project.getProjectDirectory().getFileObject("config");
        if (configDir == null) {
            return false;
        } else {
            return FileUtil.isParentOf(configDir, file);
        }

    }

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        Node[] nodes = manager.getSelectedNodes();
        for (Node node : nodes) {
            var element = ((ElementNode) node).getElement();
            elements.remove(element);
        }
        refreshView();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void addLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLineButtonActionPerformed
        var lineInput = new NotifyDescriptor.InputLine(
                Bundle.LBL_addLine(), Bundle.LBL_enterInputLine());
        var ret = DialogDisplayer.getDefault().notify(lineInput);
        if (ret == NotifyDescriptor.OK_OPTION) {
            try {
                var line = ExecutionElement.forLine(lineInput.getInputText());
                elements.add(line);
                refreshView();
            } catch (Exception ex) {
                ProjectDialogManager.get(project).reportError(Bundle.ERR_invalidLine());
            }
        }
    }//GEN-LAST:event_addLineButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFileButton;
    private javax.swing.JButton addLineButton;
    private javax.swing.JScrollPane fileList;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }

    private class ManagerListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
                if (manager.getSelectedNodes().length > 0) {
                    removeButton.setEnabled(true);
                } else {
                    removeButton.setEnabled(false);
                }
            }
        }
    }

    private class Elements extends Children.Keys<ExecutionElement> {

        void setElements(List<ExecutionElement> keys) {
            setKeys(keys);
        }

        @Override
        protected Node[] createNodes(ExecutionElement element) {
            if (element instanceof ExecutionElement.File) {
                var fileElement = (ExecutionElement.File) element;
                Node node;
                if (isConfigFile(fileElement.file())) {
                    node = new FileNode(fileElement, true);
                } else {
                    node = new FileNode(fileElement, false);
                }
                return new Node[]{node};
            } else if (element instanceof ExecutionElement.Line) {
                return new Node[]{new LineNode((ExecutionElement.Line) element)};
            }
            return new Node[0];
        }
    }

    private class ElementNode extends AbstractNode {

        private final ExecutionElement element;

        private ElementNode(ExecutionElement element) {
            super(Children.LEAF);
            this.element = element;
        }

        ExecutionElement getElement() {
            return element;
        }

    }

    private class FileNode extends ElementNode {

        private final FileObject file;
        private final boolean config;

        private FileNode(ExecutionElement.File element, boolean config) {
            super(element);
            this.file = element.file();
            this.config = config;
        }

        @Override
        public Action[] getActions(boolean context) {
            return new Action[0];
        }

        @Override
        public Image getIcon(int type) {
            try {
                return DataObject.find(file).getNodeDelegate().getIcon(type);
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
            return super.getIcon(type);
        }

        @Override
        public String getName() {
            return file.getPath();
        }

        @Override
        public String getDisplayName() {
            return FileUtil.getRelativePath(project.getProjectDirectory(), file);
        }

        @Override
        public String getHtmlDisplayName() {
            if (config) {
                return "<i>" + getDisplayName() + "</i>";
            } else {
                return null;
            }
        }
    }

    private class LineNode extends ElementNode {

        private final String line;

        public LineNode(ExecutionElement.Line element) {
            super(element);
            this.line = element.line();
        }

        @Override
        public String getDisplayName() {
            return line;
        }

    }
}
