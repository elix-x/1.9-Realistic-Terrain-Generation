package teamrtg.rtg.mods.vanilla.biomes;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.BiomeGenBase;
import teamrtg.rtg.api.util.BiomeUtils;
import teamrtg.rtg.util.noise.CellNoise;
import teamrtg.rtg.util.noise.OpenSimplexNoise;
import teamrtg.rtg.world.biome.surface.part.CliffSelector;
import teamrtg.rtg.world.biome.surface.part.SurfacePart;
import teamrtg.rtg.world.biome.terrain.GroundEffect;
import teamrtg.rtg.world.biome.terrain.TerrainBase;
import teamrtg.rtg.world.gen.ChunkProviderRTG;
import teamrtg.rtg.world.gen.deco.DecoBaseBiomeDecorations;

public class RealisticBiomeVanillaSunflowerPlains extends RealisticBiomeVanillaBase {
    public static BiomeGenBase standardBiome = Biomes.PLAINS;
    public static BiomeGenBase mutationBiome = BiomeGenBase.getBiome(BiomeUtils.getId(standardBiome) + MUTATION_ADDEND);

    public RealisticBiomeVanillaSunflowerPlains() {
        super(
                mutationBiome,
            Biomes.RIVER
        );
    }

    @Override
    protected TerrainBase initTerrain() {
        return new TerrainBase() {
            private final GroundEffect groundEffect = new GroundEffect(4f);

            @Override
            public float generateNoise(ChunkProviderRTG provider, int x, int y, float border, float river) {
                return riverized(65f + groundEffect.added(provider.simplex, provider.cell, x, y), river);
            }
        };
    }

    @Override
    protected SurfacePart initSurface() {
        SurfacePart surface = new SurfacePart();
        surface.add(new CliffSelector(1.5f)
            .add(PARTS.STONE_OR_COBBLE));
        surface.add(PARTS.surfaceGeneric());
        return surface;
    }

    @Override
    protected void initDecos() {
		DecoBaseBiomeDecorations decoBaseBiomeDecorations = new DecoBaseBiomeDecorations();
		this.addDeco(decoBaseBiomeDecorations);
    }

    @Override
    protected void initProperties() {

    }
}
