package teamrtg.rtg.mods.vanilla.biomes;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import teamrtg.rtg.util.math.CanyonColour;
import teamrtg.rtg.util.noise.CellNoise;
import teamrtg.rtg.util.noise.OpenSimplexNoise;
import teamrtg.rtg.world.biome.surface.SurfaceRiverOasis;
import teamrtg.rtg.world.biome.surface.part.*;
import teamrtg.rtg.world.biome.terrain.TerrainBase;
import teamrtg.rtg.world.gen.ChunkProviderRTG;
import teamrtg.rtg.world.gen.deco.*;

public class RealisticBiomeVanillaSavannaPlateau extends RealisticBiomeVanillaBase {

    public RealisticBiomeVanillaSavannaPlateau() {

        super(
                Biomes.SAVANNA_PLATEAU,
            Biomes.RIVER
        );
        this.noLakes = true;
    }

    @Override
    protected TerrainBase initTerrain() {
        return new TerrainBase() {
            private float[] height = new float[] {12.0f, 0.5f, 8f, 0.7f};
            private int heightLength = height.length;
            private float strength = 10f;

            @Override
            public float generateNoise(ChunkProviderRTG provider, int x, int y, float border, float river) {
                return terrainPlateau(x, y, provider.simplex, river, height, border, strength, heightLength, 50f, true);
            }
        };
    }

    @Override
    protected SurfacePart initSurface() {
        SurfacePart surface = new SurfacePart();
        surface.add(new SurfaceRiverOasis(this));
        surface.add(new DepthSelector(0, 11)
            .add(new OrSelector()
                .or(new CliffSelector(1.3f))
                .or(new DepthSelector(4, 256))
                .add(new BlockPart(CanyonColour.SAVANNA)))
            .add(PARTS.selectTop()
                .add(PARTS.rand(5)
                    .add(new BlockPart(Blocks.GRASS.getDefaultState())))
                .add(PARTS.rand(3)
                    .add(new BlockPart(Blocks.DIRT.getStateFromMeta(1)))))
        );
        surface.add(PARTS.surfaceGeneric());
        surface.add(
            new HeightSelector(50, 256).setMinNoise(PARTS.DEPTH_NOISE)
                .add(new BlockPart(CanyonColour.SAVANNA))
        );
        return surface;
    }

    @Override
    protected void initDecos() {
        DecoBoulder decoBoulder1 = new DecoBoulder();
        decoBoulder1.boulderBlock = Blocks.COBBLESTONE;
        decoBoulder1.maxY = 80;
        decoBoulder1.chance = 24;
        this.addDeco(decoBoulder1);

        DecoBoulder decoBoulder2 = new DecoBoulder();
        decoBoulder2.boulderBlock = Blocks.COBBLESTONE;
        decoBoulder1.minY = 110;
        decoBoulder2.chance = 24;
        this.addDeco(decoBoulder2);

        DecoTree riverTrees = new DecoTree();
        riverTrees.checkRiver = true;
        riverTrees.minRiver = 0.86f;
        riverTrees.strengthFactorForLoops = 10f;
        riverTrees.treeType = DecoTree.TreeType.SAVANNA_RIVER;
        riverTrees.treeCondition = DecoTree.TreeCondition.RANDOM_CHANCE;
        riverTrees.treeConditionChance = 4;
        riverTrees.maxY = 120;
        this.addDeco(riverTrees);

        DecoCactus decoRiverCactus = new DecoCactus();
        decoRiverCactus.checkRiver = true;
        decoRiverCactus.minRiver = 0.7f;
        decoRiverCactus.maxY = 80;
        decoRiverCactus.strengthFactor = 12f;
        this.addDeco(decoRiverCactus);

        DecoReed decoReed = new DecoReed();
        decoReed.checkRiver = true;
        decoReed.minRiver = 0.7f;
        decoReed.maxY = 68;
        decoReed.strengthFactor = 2f;
        this.addDeco(decoReed);

        DecoFlowersRTG decoFlowersRTG = new DecoFlowersRTG();
        decoFlowersRTG.checkRiver = true;
        decoFlowersRTG.minRiver = 0.7f;
        decoFlowersRTG.flowers = new int[] {9, 9, 9, 9, 3, 3, 3, 3, 3, 2, 2, 2, 11, 11, 11};
        decoFlowersRTG.maxY = 128;
        decoFlowersRTG.loops = 3;
        this.addDeco(decoFlowersRTG);

        DecoGrassDoubleTallgrass decoGrassDoubleTallgrass = new DecoGrassDoubleTallgrass();
        decoGrassDoubleTallgrass.checkRiver = true;
        decoGrassDoubleTallgrass.minRiver = 0.7f;
        decoGrassDoubleTallgrass.maxY = 128;
        decoGrassDoubleTallgrass.loops = 15;
        decoGrassDoubleTallgrass.doubleGrassChance = 3;
        this.addDeco(decoGrassDoubleTallgrass);

        DecoTree savannaTrees = new DecoTree();
        savannaTrees.strengthFactorForLoops = 3f;
        savannaTrees.treeType = DecoTree.TreeType.SAVANNA;
        savannaTrees.treeCondition = DecoTree.TreeCondition.RANDOM_CHANCE;
        savannaTrees.treeConditionChance = 3;
        savannaTrees.maxY = 160;
        this.addDeco(savannaTrees);

        DecoCactus decoCactus = new DecoCactus();
        decoCactus.maxY = 160;
        decoCactus.loops = 60;
        decoCactus.chance = 8;
        this.addDeco(decoCactus);

        DecoDoubleGrass decoDoubleGrass = new DecoDoubleGrass();
        decoDoubleGrass.maxY = 128;
        decoDoubleGrass.strengthFactor = 3f;
        this.addDeco(decoDoubleGrass);

        DecoGrass decoGrass = new DecoGrass();
        decoGrass.maxY = 128;
        decoGrass.strengthFactor = 10f;
        this.addDeco(decoGrass);
    }

    @Override
    protected void initProperties() {

    }
}
