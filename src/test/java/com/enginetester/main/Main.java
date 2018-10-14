package com.enginetester.main;

import com.marnixbarendregt.gamelib.controls.FirstPerson;
import com.marnixbarendregt.gamelib.controls.ThirdPerson;
import com.marnixbarendregt.gamelib.main.*;
import com.marnixbarendregt.gamelib.materials.Material;
import com.marnixbarendregt.gamelib.mesh.Cube;
import com.marnixbarendregt.gamelib.utils.Color;
import com.marnixbarendregt.gamelib.utils.OBJParser;
import org.joml.Vector3f;

import java.util.Random;

/**
 * Created by marnixbarendregt on 18/09/2017.
 */
public class Main extends Game {

    private Window window;
    private Scene scene;
    private Renderer renderer;
    private Camera camera;
    private Entity entity;


    private Main() {
        window = new Window(600, 400, "Test", true);
        window.show();

        super.init(window);
    }

    @Override
    public void init() {
        setClearColor(new Color(255, 255, 255));

        camera = new Camera(window, 50f, 0.1f, 1000.0f);

        scene = new Scene();
        renderer = new Renderer();

        entity = new Entity(Cube.getMesh(), Material.TEST, new Color(0.0f, 0.0f, 0.0f), new Vector3f(0.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
        scene.add(entity);
        scene.add(new Terrain(500, 500));

        for (int i = 0; i < 20; i++) {
            try {
                scene.add(new Entity(Cube.getMesh(), Material.TEST, new Texture("resources/textures/dirt.jpg"), new Vector3f(new Random().nextFloat() * 10.0f, 0.0f, new Random().nextFloat() * 10.0f), new Vector3f(0.0f, new Random().nextFloat() * 360, 1.0f), new Random().nextFloat() * 0.3f + 0.2f));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        useControls(new FirstPerson(camera, window,1.0f, 0.6f, true));
    }

    @Override
    public void update() {}

    @Override
    public void render() { renderer.render(scene, camera); }

    @Override
    public void onClose() {
        scene.cleanUp();
    }

    public static void main(String[] args) {
        new Main();
    }
}