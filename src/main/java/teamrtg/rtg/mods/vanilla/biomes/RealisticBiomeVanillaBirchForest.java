package teamrtg.rtg.mods.vanilla.biomes;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import teamrtg.rtg.world.biome.surface.part.CliffSelector;
import teamrtg.rtg.world.biome.surface.part.SurfacePart;
import teamrtg.rtg.world.biome.terrain.GroundEffect;
import teamrtg.rtg.world.biome.terrain.TerrainBase;
import teamrtg.rtg.world.gen.ChunkProviderRTG;
import teamrtg.rtg.world.gen.deco.*;
import teamrtg.rtg.world.gen.deco.DecoFallenTree.LogCondition;
import teamrtg.rtg.world.gen.deco.DecoTree.TreeCondition;
import teamrtg.rtg.world.gen.deco.DecoTree.TreeType;

public class RealisticBiomeVanillaBirchForest extends RealisticBiomeVanillaBase {

    public RealisticBiomeVanillaBirchForest() {
        super(
            Biomes.BIRCH_FOREST,
            Biomes.RIVER
        );
    }

    @Override
    protected void initProperties() {
        config.addBlock(config.BEACH_BLOCK).setDefault(Blocks.SAND.getDefaultState());
        config.addBlock(config.MIX_BLOCK_TOP).setDefault(Blocks.DIRT.getStateFromMeta(2));
    }

    @Override
    protected void initDecos() {
        DecoTree smallBirch = new DecoTree();
        smallBirch.strengthNoiseFactorForLoops = true;
        smallBirch.treeType = TreeType.SMALL_BIRCH;
        smallBirch.distribution.noiseDivisor = 80f;
        smallBirch.distribution.noiseFactor = 60f;
        smallBirch.distribution.noiseAddend = -15f;
        smallBirch.treeCondition = TreeCondition.ALWAYS_GENERATE;
        smallBirch.maxY = 120;
        this.addDeco(smallBirch);

        DecoTree birchTreesForest = new DecoTree();
        birchTreesForest.strengthFactorForLoops = 3f;
        birchTreesForest.treeType = TreeType.BIRCH_TREES_FOREST;
        birchTreesForest.treeCondition = TreeCondition.ALWAYS_GENERATE;
        birchTreesForest.maxY = 100;
        this.addDeco(birchTreesForest);

        DecoFallenTree decoFallenTree = new DecoFallenTree();
        decoFallenTree.logCondition = LogCondition.RANDOM_CHANCE;
        decoFallenTree.logConditionChance = 8;
        decoFallenTree.maxY = 100;
        decoFallenTree.logBlock = Blocks.LOG;
        decoFallenTree.logMeta = (byte) 2;
        decoFallenTree.leavesBlock = Blocks.LEAVES;
        decoFallenTree.leavesMeta = (byte) -1;
        decoFallenTree.minSize = 3;
        decoFallenTree.maxSize = 6;
        this.addDeco(decoFallenTree);

        DecoShrub decoShrub = new DecoShrub();
        decoShrub.maxY = 120;
        decoShrub.strengthFactor = 3f;
        this.addDeco(decoShrub);

        DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
        decoBaseBiomeDecorations.notEqualsZeroChance = 3;
        this.addDeco(decoBaseBiomeDecorations);

        DecoFlowersRTG decoFlowersRTG = new DecoFlowersRTG();
        decoFlowersRTG.flowers = new int[] {3, 6};
        decoFlowersRTG.maxY = 128;
        decoFlowersRTG.strengthFactor = 12f;
        this.addDeco(decoFlowersRTG);

        DecoGrass decoGrass = new DecoGrass();
        decoGrass.maxY = 128;
        decoGrass.strengthFactor = 20f;
        this.addDeco(decoGrass);
    }

    @Override
    protected SurfacePart initSurface() {
        SurfacePart surface = new SurfacePart();
        surface.add(new CliffSelector(1.5f)
            .add(PARTS.selectTopAndFill()
                .add(this.PARTS.SHADOW_STONE)));
        surface.add(new CliffSelector((x, y, z, provider) -> 1.5f - ((y - 60f) / 65f) + provider.simplex.noise3(x / 8f, y / 8f, z / 8f) * 0.5f)
            .add(PARTS.selectTop()
                .add(PARTS.STONE_OR_COBBLE)))
            .add(PARTS.selectFill()
                .add(PARTS.STONE));
        surface.add(PARTS.surfaceMix(PARTS.MIX_NOISE));
        surface.add(PARTS.surfaceGeneric());
        return surface;
    }

    @Override
    protected TerrainBase initTerrain() {
        return new TerrainBase() {
            private GroundEffect groundEffect = new GroundEffect(4f);

            @Override
            public float generateNoise(ChunkProviderRTG provider, int x, int y, float border, float river) {
                return riverized(65f + groundEffect.added(provider.simplex, provider.cell, x, y), river);
            }
        };
    }
}
