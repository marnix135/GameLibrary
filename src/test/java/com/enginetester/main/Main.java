package com.enginetester.main;

import com.marnixbarendregt.gamelib.controls.FirstPerson;
import com.marnixbarendregt.gamelib.controls.ThirdPerson;
import com.marnixbarendregt.gamelib.main.*;
import com.marnixbarendregt.gamelib.materials.Material;
import com.marnixbarendregt.gamelib.mesh.Cube;
import com.marnixbarendregt.gamelib.utils.Color;
import org.joml.Vector3f;

/**
 * Created by marnixbarendregt on 18/09/2017.
 */
public class Main extends Game {

    private Window window;
    private Scene scene;
    private Renderer renderer;
    private Camera camera;
    private Entity entity;
    private Vector3f sunPos;
    private float count;


    private Main() {
        window = new Window(600, 400, "Test", true);
        window.show();

        super.init(window);
    }

    @Override
    public void init() {
        setClearColor(new Color(255, 255, 255));

        camera = new Camera(window, 50f, 0.1f, 500.0f);

        scene = new Scene();
        renderer = new Renderer();

        sunPos = new Vector3f(250.0f, 100.0f, 250.0f);

        scene.setSunPos(sunPos);
        scene.useSun(true);

        //entity = new Entity(Cube.getMesh(), Material.TEST, new Color(0.0f, 0.0f, 0.0f), new Vector3f(0.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
        //scene.add(entity);
        Terrain terrain = new Terrain(500, 500);
        scene.add(terrain);
        camera.setPosition(new Vector3f(250.0f, terrain.getHeightAt(250.0f, 250.0f) + 3.0f, 250.0f));

        Entity player = new Entity(Cube.getMesh(), Material.TEST, new Texture("resources/textures/dirt.jpg"), new Vector3f(250.0f, terrain.getHeightAt(250.0f, 250.0f) + 3.0f, 250.0f), new Vector3f(), 1.0f);
        scene.add(player);
        scene.addFog(new Vector3f(0.9f, 0.91f, 0.93f));
        /*for (int i = 0; i < 20; i++) {
            try {
                scene.add(new Entity(Cube.getMesh(), Material.TEST, new Texture("resources/textures/dirt.jpg"), new Vector3f(new Random().nextFloat() * 10.0f, 0.0f, new Random().nextFloat() * 10.0f), new Vector3f(0.0f, new Random().nextFloat() * 360, 1.0f), new Random().nextFloat() * 0.3f + 0.2f));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        useControls(new FirstPerson(camera, window, terrain, 0.1f, 0.1f, true, true, 1.2f));
        //useControls(new ThirdPerson(camera, window, player, 0.1f, 0.1f));
    }

    @Override
    public void update() {
        sunPos = new Vector3f((float) (250.0f + 50.0f * Math.cos(count)), 100.0f, (float) (250.0f + 50.0f * Math.sin(count)));
        scene.setSunPos(sunPos);
        count+= 0.005f;
    }

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
