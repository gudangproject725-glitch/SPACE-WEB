package com.example.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.R

enum class SpaceSection(
    val indexLabel: String,
    val navTitle: String,
    val listIndex: Int
) {
    HERO("01", "HERO", 0),
    VISION("02", "VISION", 1),
    EXTERIOR("03", "EXTERIOR", 2),
    ENGINEERING("04", "ENGINEERING", 3),
    INTERIOR("05", "INTERIOR", 4),
    EXPERIENCE("06", "EXPERIENCE", 5),
    SPECIFICATIONS("07", "SPECIFICATIONS", 6),
    CONTACT("08", "CONTACT", 7)
}

data class VisionPillar(
    val code: String,
    val title: String,
    val summary: String,
    val metricLabel: String,
    val metricValue: String
)

data class AeroHotspot(
    val id: String,
    val code: String,
    val title: String,
    val description: String,
    val metric: String,
    val xFraction: Float,
    val yFraction: Float
)

data class DriveModeSpec(
    val id: String,
    val name: String,
    val subtitle: String,
    val powerOutputHp: Int,
    val rideHeightMm: String,
    val aeroProfile: String,
    val torqueSplit: String,
    val curveControlPoints: List<Float>
)

data class InteriorMaterialOption(
    val id: String,
    val name: String,
    val origin: String,
    val description: String,
    val weightAdvantage: String,
    val primarySwatch: Color,
    val accentSwatch: Color
)

data class GalleryItem(
    val id: String,
    val indexLabel: String,
    val title: String,
    val caption: String,
    val studioMetadata: String,
    @DrawableRes val imageRes: Int
)

enum class SpecCategory(val label: String) {
    ALL("ALL"),
    POWERTRAIN("POWERTRAIN"),
    CHASSIS("CHASSIS"),
    DIMENSIONS("DIMENSIONS")
}

data class VehicleSpec(
    val label: String,
    val value: String,
    val category: SpecCategory
)

data class AtelierLocation(
    val city: String,
    val code: String,
    val address: String,
    val status: String
)

object SpaceShowcaseRepository {
    val visionPillars = listOf(
        VisionPillar(
            code = "P-01",
            title = "AERO SCULPTURE",
            summary = "Every surface channels high-velocity laminar airflow without external appendages breaking the silhouette.",
            metricLabel = "DRAG COEFFICIENT",
            metricValue = "0.21 Cd"
        ),
        VisionPillar(
            code = "P-02",
            title = "CARBON MONOCOQUE",
            summary = "Autoclave pre-preg carbon tub engineered as a singular structural sculpture for torsional rigidity.",
            metricLabel = "TORSIONAL STIFFNESS",
            metricValue = "68,000 Nm/deg"
        ),
        VisionPillar(
            code = "P-03",
            title = "NOCTURNAL LUXURY",
            summary = "Obsidian finishes, brushed titanium switchgear, and silent electric torque tuned for effortless composure.",
            metricLabel = "ATELIER EDITION",
            metricValue = "25 UNITS"
        )
    )

    val aeroHotspots = listOf(
        AeroHotspot(
            id = "splitter",
            code = "AERO 01",
            title = "ACTIVE FRONT SPLITTER",
            description = "Electro-hydraulic front blade adjusts pitch by 14 degrees above 180 km/h to balance front-axle grip.",
            metric = "+310 KG FRONT LOAD",
            xFraction = 0.16f,
            yFraction = 0.71f
        ),
        AeroHotspot(
            id = "venturi",
            code = "AERO 02",
            title = "VENTURI SIDE CHANNELS",
            description = "Deep undercut door architecture accelerates boundary-layer air directly into the rear diffuser.",
            metric = "2,100 L/MIN FLOW",
            xFraction = 0.54f,
            yFraction = 0.58f
        ),
        AeroHotspot(
            id = "wing",
            code = "AERO 03",
            title = "ADAPTIVE REAR WING",
            description = "Seamless swan-neck active airfoil deploys DRS low-drag profile or high-downforce airbrake in 120ms.",
            metric = "+840 KG TOTAL @ 300 KM/H",
            xFraction = 0.87f,
            yFraction = 0.42f
        )
    )

    val driveModes = listOf(
        DriveModeSpec(
            id = "strada",
            name = "STRADA",
            subtitle = "Grand touring composure with adaptive damping and silent torque delivery.",
            powerOutputHp = 720,
            rideHeightMm = "115 MM",
            aeroProfile = "LOW DRAG",
            torqueSplit = "35F / 65R",
            curveControlPoints = listOf(0.28f, 0.46f, 0.62f, 0.72f, 0.76f, 0.73f)
        ),
        DriveModeSpec(
            id = "corsa",
            name = "CORSA",
            subtitle = "Track-calibrated yaw vectoring, stiffened pushrod kinematics, and active aero lock.",
            powerOutputHp = 940,
            rideHeightMm = "85 MM",
            aeroProfile = "HIGH DOWNFORCE",
            torqueSplit = "42F / 58R",
            curveControlPoints = listOf(0.42f, 0.68f, 0.84f, 0.91f, 0.92f, 0.88f)
        ),
        DriveModeSpec(
            id = "space_plus",
            name = "SPACE+",
            subtitle = "Unrestricted quad-motor overboost unleashing peak 1,040 HP telemetry.",
            powerOutputHp = 1040,
            rideHeightMm = "75 MM",
            aeroProfile = "ACTIVE DRS +",
            torqueSplit = "48F / 52R",
            curveControlPoints = listOf(0.55f, 0.82f, 0.95f, 1.00f, 0.99f, 0.96f)
        )
    )

    val interiorMaterials = listOf(
        InteriorMaterialOption(
            id = "ivory_alcantara",
            name = "OBSIDIAN & IVORY ALCANTARA",
            origin = "MILAN ATELIER WEAVE",
            description = "Hand-stitched warm ivory Alcantara bolsters paired with matte acoustic carbon weave.",
            weightAdvantage = "-14.2 KG VS HIDE",
            primarySwatch = Color(0xFF1A1A1A),
            accentSwatch = Color(0xFFEAE6DF)
        ),
        InteriorMaterialOption(
            id = "forged_carbon",
            name = "RAW FORGED CARBON",
            origin = "GENEVA COMPOSITE LAB",
            description = "Unlacquered satin forged carbon monocoque surfaces revealing raw high-modulus fibers.",
            weightAdvantage = "-22.8 KG MONOCOQUE",
            primarySwatch = Color(0xFF262626),
            accentSwatch = Color(0xFF5A5A5A)
        ),
        InteriorMaterialOption(
            id = "titanium_monolith",
            name = "TITANIUM MONOLITH",
            origin = "SWISS PRECISION MILL",
            description = "Grade-5 CNC-milled titanium paddles, rotary telemetry dials, and structural yoke core.",
            weightAdvantage = "GRADE-5 TI-6AL-4V",
            primarySwatch = Color(0xFF3A3936),
            accentSwatch = Color(0xFFB7B0A5)
        )
    )

    val galleryItems = listOf(
        GalleryItem(
            id = "front_34",
            indexLabel = "01 / 04",
            title = "FRONT THREE-QUARTER STUDY",
            caption = "Signature warm-ivory LED blades framing the dual carbon intake plenum.",
            studioMetadata = "50MM · F/2.8 · OBSIDIAN STUDIO STAGE A",
            imageRes = R.drawable.img_hero_hypercar_1795538366516
        ),
        GalleryItem(
            id = "side_profile",
            indexLabel = "02 / 04",
            title = "SILHOUETTE & VENTURI LINE",
            caption = "Teardrop canopy flow sweeping uninterrupted into forged turbine wheels.",
            studioMetadata = "85MM · F/4.0 · WIND TUNNEL LINEAR RIG",
            imageRes = R.drawable.img_exterior_profile_1795538367658
        ),
        GalleryItem(
            id = "engineering_bay",
            indexLabel = "03 / 04",
            title = "PUSHROD & CARBON BAY",
            caption = "Billet titanium suspension kinematics coupled directly to the carbon tub.",
            studioMetadata = "90MM MACRO · F/5.6 · TELEMETRY LAB",
            imageRes = R.drawable.img_engineering_powertrain_1795538367934
        ),
        GalleryItem(
            id = "cockpit_view",
            indexLabel = "04 / 04",
            title = "PILOT MONOLITH COCKPIT",
            caption = "Driver-centric ergonomics in ivory Alcantara and billet titanium.",
            studioMetadata = "28MM · F/2.0 · ATELIER INTERIOR RIG",
            imageRes = R.drawable.img_interior_cockpit_1795538368482
        )
    )

    val specifications = listOf(
        VehicleSpec("VEHICLE MODEL", "SPACE ONE", SpecCategory.POWERTRAIN),
        VehicleSpec("POWERTRAIN", "Quad-Motor Electric Performance", SpecCategory.POWERTRAIN),
        VehicleSpec("PEAK OUTPUT", "1,040 HP (775 kW)", SpecCategory.POWERTRAIN),
        VehicleSpec("0–100 KM/H", "2.5 SEC", SpecCategory.POWERTRAIN),
        VehicleSpec("TOP SPEED", "350+ KM/H", SpecCategory.POWERTRAIN),
        VehicleSpec("DRIVETRAIN", "Active Torque-Vectoring AWD", SpecCategory.POWERTRAIN),
        VehicleSpec("CHASSIS", "Autoclave Carbon Composite Monocoque", SpecCategory.CHASSIS),
        VehicleSpec("SUSPENSION", "Titanium Double-Wishbone Pushrod", SpecCategory.CHASSIS),
        VehicleSpec("BRAKING SYSTEM", "410mm Carbon-Ceramic 6-Piston", SpecCategory.CHASSIS),
        VehicleSpec("WHEELBASE", "2,740 MM", SpecCategory.DIMENSIONS),
        VehicleSpec("LENGTH × WIDTH", "4,680 × 2,010 MM", SpecCategory.DIMENSIONS),
        VehicleSpec("DRY WEIGHT", "1,380 KG", SpecCategory.DIMENSIONS),
        VehicleSpec("ALLOCATION", "Limited to 25 Units Worldwide", SpecCategory.DIMENSIONS)
    )

    val atelierLocations = listOf(
        AtelierLocation("GENEVA", "GVA", "Rue du Rhône 42", "PRIVATE APPOINTMENTS"),
        AtelierLocation("MONACO", "MCM", "Avenue Princesse Grace 17", "ATELIER LOUNGE"),
        AtelierLocation("TOKYO", "TYO", "Minami-Aoyama 5-7", "DESIGN STUDIO"),
        AtelierLocation("LOS ANGELES", "LAX", "Beverly Drive 9400", "COMMISSION SUITE")
    )

    val experienceTypes = listOf(
        "PRIVATE STUDIO VIEWING",
        "TRACK TELEMETRY SESSION",
        "BESPOKE COMMISSION"
    )
}
