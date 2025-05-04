package takia5.playerClient.sceneTree;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.model.Node;

public class SceneTree {
    Camera camera;
    Environment environment;
    Node root = new Node();
    Node cameraContainer = new Node();

    public SceneTree() {
        camera = new OrthographicCamera(0, 0);
        environment = new Environment();
        root.addChild(cameraContainer);
    }

}
